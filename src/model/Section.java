package model;

public class Section {

	private String name;
	private String number;
	private int index;
	private String content;
	
	public Section(String name, String number, int index){
		this.setName(name);
		this.setNumber(number);
		this.setIndex(index);
		this.setContent("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
    @Override
    public String toString() {
        String result;
        
        result  = "Name: " + name + "\n";
        result += "Number: " + number + "\n";
        result += "Index: " + Integer.toString(index) + "\n";
        result += "Content: " + content + "\n";
        
        return result;
    }	
}
