package model;

import java.util.ArrayList;

public class City {
	private String name;
	private ArrayList<Section> sections;
	
	public City(){
		setName("");
		setSections(new ArrayList<Section>());
	}
	
	public City(String name){
		setName(name);
		setSections(new ArrayList<Section>());
		
	}

	public ArrayList<Section> getSections() {
		return sections;
	}

	public void setSections(ArrayList<Section> sections) {
		this.sections = sections;
	}
	
	public void addSection(Section section){
		this.sections.add(section);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
