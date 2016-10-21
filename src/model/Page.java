package model;

public class Page {
	private int pageID;
	private String pageTitle;
	
	public Page(int pageID, String pageTitle){
		this.setPageID(pageID);
		this.setPageTitle(pageTitle);
	}
	
	public Page(String pageTitle){
		this.setPageID(-1);
		this.setPageTitle(pageTitle);
	}

	public int getPageID() {
		return pageID;
	}

	public void setPageID(int pageID) {
		this.pageID = pageID;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	
}
