package org.session.extractor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SessionExtractor {

	private final List<Session> allSessions;
	private Elements sessions;
	private final String KCDC_2013 = "http://kcdc.info";
	private static final String SESSION_PAGE = "http://kcdc.info/sessions";
	private static Logger log = Logger.getLogger( SessionExtractor.class );

	public SessionExtractor() {

		allSessions = new LinkedList<Session>();
		getSessions();
		createSessionObjects();
	}

	private void getSessions() {

		try {
			sessions = Jsoup.connect( SESSION_PAGE ).get().getElementsByTag( "article" );
		}
		catch ( IOException e ) {
			log.error( e );
		}
	}

	private void createSessionObjects() {

		log.debug( "total session: " + sessions.size() );
		int count = 1;

		for ( Element element : sessions ) {
			Session session = new Session();
			session.setSessionName( element.text() );
			setSessionRelatedData( element.text(), session );
			setSpeakerNameAndImage( element.childNode( 0 ).attr( "href" ), session );

			allSessions.add( session );

			log.debug( count + " / " + sessions.size() + " is done" );
			count++;
		}
	}

	private void setSessionRelatedData(String str, Session session) {

		String[] each = str.split( "/" );
		session.setSessionName( each[0].substring( 0, each[0].length() - 4 ) );
		setTimeRoomAndLength( each[1], session );
	}

	private void setTimeRoomAndLength(String str, Session session) {

		String[] each = str.split( "\\|" );
		session.setDate( each[0].substring( 5, each[0].length() - 1 ) );
		session.setRoom( each[1].substring( 6, each[1].length() - 1 ) );
		session.setLength( each[2].substring( 9, each[2].length() ) );
	}

	private void setSpeakerNameAndImage(String sessionPage, Session session) {

		try {
			Elements elements = Jsoup.connect( KCDC_2013 + sessionPage ).get().getElementsByClass( "main-content" );
			session.setSpeaker( elements.get( 0 ).childNode( 2 ).childNode( 1 ).childNode( 0 ).toString() );
			setSpeakerImage( elements.get( 0 ).childNode( 2 ).childNode( 1 ).attr( "href" ), session );
		}
		catch ( IOException e ) {
			log.error( e );
		}
	}

	private void setSpeakerImage(String speakerPage, Session session) {

		try {
			Elements elements = Jsoup.connect( KCDC_2013 + speakerPage ).get().getElementsByClass( "speaker-bio" );
			doSetSpeakerImage( elements.get( 0 ).childNode( 0 ).attr( "src" ), session );
		}
		catch ( IOException e ) {
			log.error( e );
		}
	}

	private void doSetSpeakerImage(String url, Session session) {

		if ( url.startsWith( "http" ) || url.startsWith( "https" ) ) {
			session.setImageUrl( url );
		}
		else if ( url.startsWith( "/" ) ) {
			session.setImageUrl( KCDC_2013 + url );
		}
		else {
			session.setImageUrl( "" );
		}
	}

	public List<Session> getAllSessions() {
		return allSessions;
	}
}
