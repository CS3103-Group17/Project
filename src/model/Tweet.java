package model;

import java.util.ArrayList;

public class Tweet {

	private String tweet;
	private String username;
	private String date;
	private ArrayList<String> images;
	
	public Tweet(String tweet, String username, String date){
		setTweet(tweet);
		setUsername(username);
		setDate(date);
		images = new ArrayList<String>();
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<String> getImages() {
		return images;
	}

	public void addImage(String image) {
		images.add(image);
	}
}
