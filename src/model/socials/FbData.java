package model.socials;

public class FbData {
	
	private int ID;
	private String userHandle;
	private String text;
	private String sourceURL;
	private String imageURL;
	
	/**
	 * 
	 * @param iD
	 * @param userHandle
	 * @param caption
	 * @param sourceURL
	 * @param imageURL
	 */
	
	public FbData(int iD, String userHandle, String caption, String sourceURL, String imageURL) {
		ID = iD;
		this.userHandle = userHandle;
		this.text = text;
		this.sourceURL = sourceURL;
		this.imageURL = imageURL;
	}

	public int getID() {
		return ID;
	}

	public String getUserHandle() {
		return userHandle;
	}

	public String getText() {
		return text;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public String getImageURL() {
		return imageURL;
	}
	
	public String toString(){
		return "#"+ID+" @"+userHandle+" "+text+" "+sourceURL+" "+imageURL+"\n";
	}

}
