package model;

public class ImageData {

	private String url;
	private String name;
	private String description;
	
	public ImageData(){
		setName("");
		setURL("");
		setDescription("");
	}
	
	public ImageData(String name, String url, String description){
		setName(name);
		setURL(url);
		setDescription(description);
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
