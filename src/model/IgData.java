package model;

public class IgData {
	private int ID;
	private String userHandle;
	private String caption;
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
	
	public IgData(int iD, String userHandle, String caption, String sourceURL, String imageURL) {
		ID = iD;
		this.userHandle = userHandle;
		this.caption = caption;
		this.sourceURL = sourceURL;
		this.imageURL = imageURL;
	}

	public int getID() {
		return ID;
	}

	public String getUserHandle() {
		return userHandle;
	}

	public String getCaption() {
		return caption;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public String getImageURL() {
		return imageURL;
	}
	
	public String toString(){
		return "#"+ID+" @"+userHandle+" "+caption+" "+sourceURL+" "+imageURL+"\n";
	}
	
}
