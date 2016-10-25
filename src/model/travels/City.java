package model.travels;

import java.util.ArrayList;
import java.util.UUID;

import model.ImageData;
import model.Section;

public class City {
    private final UUID cityId;
    
	private String name;
	private String summaryContent;
	private String googleTrendsHTML;
	private ArrayList<Section> sections;
	private ArrayList<ImageData> images;
	
	public City() {
	    this.cityId = UUID.randomUUID();

	    this.name = "";
	    this.summaryContent = "";
	    this.googleTrendsHTML = "";
	    this.sections = new ArrayList<Section>();
	    this.images = new ArrayList<ImageData>();
	}
	
	/* Used when loading city from cache. */
	public City(UUID cityId) {
	    this.cityId = cityId;

        this.name = "";
        this.summaryContent = "";
        this.googleTrendsHTML = "";
        this.sections = new ArrayList<Section>();
        this.images = new ArrayList<ImageData>();
	}

	public City(String name) {
	    this();
	    this.name = name;
	}
	
	public UUID getCityId() {
	    return cityId;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }	
	
    public String getSummaryContent() {
        return summaryContent;
    }

    public void setSummaryContent(String summaryContent) {
        this.summaryContent = summaryContent;
    } 
    
    public String getGoogleTrendsHTML() {
        return googleTrendsHTML;
    }

    public void setGoogleTrendsHTML(String googleTrendsHTML) {
        this.googleTrendsHTML = googleTrendsHTML;
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
	
	public Section findSectionByName(String name) {
		for(Section section : sections){
			if(section.getName().equals(name)){
				return section;
			}
		}
		
		return null;
	}

	public ArrayList<ImageData> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageData> images) {
		this.images = images;
	}
	
	public void addImage(ImageData image) {
		images.add(image);
	}
	
	public void addImages(ArrayList<ImageData> images) {
		this.images.addAll(images);		
	}
	
	@Override
	public String toString() {
	    String result;
	    
	    result  = "UUID: " + cityId.toString() + "\n";
	    result += "Name: " + name + "\n";
	    
	    return result;
	}
}
