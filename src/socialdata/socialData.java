package socialdata;

public class socialData {
	
	private int ID;
	private String userHandle;
	private String text;
	private MediaType mediaType;
	private String mediaURL;
	
	/**
	 * create a new social post data set
	 * @param ID - unique identifier for the post 
	 * @param userHandle - user name who created the post
	 * @param text - text content of the post
	 * @param mediaType - enum to identify post media type
	 * @param mediaURL - URL of media
	 */
	public socialData(int ID, String userHandle, String text, MediaType mediaType, String mediaURL){
		this.ID = ID;
		this.userHandle = userHandle;
		this.text = text;
		this.mediaType = mediaType;
		this.mediaURL = mediaURL;
	}
	
	public int getID(){
		return ID;
	}
	
	public String getUserHandle() {
		return userHandle;
	}
	public String getText() {
		return text;
	}
	public String getMediaURL() {
		return mediaURL;
	}
	public MediaType getMediaType() {
		return mediaType;
	}
	
	public enum MediaType{
		NONE, URL, IMAGE, VIDEO	
	}
}
