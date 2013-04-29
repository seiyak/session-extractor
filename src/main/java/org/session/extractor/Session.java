package org.session.extractor;

public class Session {

	private String speaker;
	private String sessionName;
	private String imageUrl;
	private String date;
	private String room;
	private String length;

	public Session(String speaker, String sessionName, String imageUrl, String date, String room, String length) {
		this.speaker = speaker;
		this.sessionName = sessionName;
		this.imageUrl = imageUrl;
		this.date = date;
		this.room = room;
		this.length = length;
	}

	public Session() {

	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Override
	public boolean equals(Object obj) {

		if ( obj instanceof Session ) {
			Session session = (Session) obj;
			return speaker.equals( session.getSpeaker() ) && sessionName.equals( session.getSessionName() )
					&& imageUrl.equals( session.getImageUrl() ) && date.equals( session.getDate() )
					&& room.equals( session.getRoom() ) && length.equals( session.getLength() );
		}

		return false;
	}

	@Override
	public String toString() {
		return "{speaker=" + speaker + ", sessionName=" + sessionName + ", imageUrl=" + imageUrl + ", date=" + date
				+ ", room=" + room + ", length=" + length + "}";
	}
}
