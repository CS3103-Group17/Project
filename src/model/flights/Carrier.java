package model.flights;

public class Carrier {

	private String id;
	private String code;
	private String name;
	private String imageURL;
	private String displayCode;
	
	public Carrier(String id, String code, String name, String imageURL, String displayCode){
		setID(id);
		setCode(code);
		setName(name);
		setImageURL(imageURL);
		setDisplayCode(displayCode);
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}
	
}
