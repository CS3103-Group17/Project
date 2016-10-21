package model;

import java.util.ArrayList;

public class City {
	private String name;
	private String summaryContent;
	private ArrayList<Section> sections;
	private ArrayList<ImageData> images;
	private ArrayList<Tweet> tweets;
	
	public City(){
		setName("");
		setSummaryContent("");
		setSections(new ArrayList<Section>());
		setImages(new ArrayList<ImageData>());
		tweets = new ArrayList<Tweet>();
	}
	
	public City(String name){
		setName(name);
		setSummaryContent("");
		setSections(new ArrayList<Section>());
		setImages(new ArrayList<ImageData>());
		tweets = new ArrayList<Tweet>();
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
	
	public Section findSection(String sectionName){
		for(Section s : sections){
			if(s.getName().equals(sectionName)){
				return s;
			}
		}
		
		return null;
	}

	public String getSummaryContent() {
		return summaryContent;
	}

	public void setSummaryContent(String summaryContent) {
		this.summaryContent = summaryContent;
	}

	public ArrayList<ImageData> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageData> images) {
		this.images = images;
	}
	
	public void addImage(ImageData image){
		images.add(image);
	}

	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	public void addTweet(Tweet tweet) {
		tweets.add(tweet);
	}
}
