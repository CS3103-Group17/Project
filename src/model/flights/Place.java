package model.flights;

public class Place {

	private String id;
	private String parentID;
	private String code;
	private String type;
	private String name;
	
	public Place (String id, String parentID, String code, String type, String name){
		setID(id);
		setParentID(parentID);
		setCode(code);
		setType(type);
		setName(name);
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
