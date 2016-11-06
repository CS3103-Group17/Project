package model.socials;

public class FbData {
	
	private int ID;
	private String userHandle;
	private String text;
	private String sourceURL;
	private String textURL;
	private String imageURL;

	/**
	 * 
	 * @param iD
	 * @param userHandle
	 * @param text
	 * @param sourceURL
	 */
	public FbData(int iD, String userHandle, String text, String sourceURL) {
		ID = iD;
		this.userHandle = userHandle;
		this.text = text;
		this.sourceURL = sourceURL;
		this.textURL = null;
		this.imageURL = null;
	}
	
	public void addTextURL(String url){
		this.textURL = url;
	}
	
	public void addImageURL(String url){
		this.imageURL = url;
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

	public String getSourceURL() {
		return sourceURL;
	}

	public String getTextURL() {
		return textURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String toString(){
		return "#"+ID+" "+"@"+userHandle+" "+text+"\n"+textURL+" "+imageURL+"\n";
		
	}

}
