package twitter;

import java.util.LinkedList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import model.TweetData;

public class TwitterCrawler {
	public final static String ConsumerKey = "splkcDS1sIUD73XoxojCUkAZz";
	public final static String ConsumerSecret = "pwClsMLOBnMIGeRJXvmKUW5aFkCTOcnWo1jgN2xoIdym2dfOD5";
	public final static String AccessToken = "785738937620803588-lmCTgOlXycaJaflvmfq9D8zH67nHsFe";
	public final static String AccessScret = "PPhb08AChoiwjoXTt36mNNjDy3YeQWA6gsPIUx7z03cnj";

	private Twitter twitter;

	/**
	 * search and retrieve tweets using the search term	
	 * @param searchTerm
	 * @param tweetsCount
	 * @return a linkedlist of tweetData
	 */
	public LinkedList<TweetData> getTweets(String searchTerm, int tweetsCount){
		LinkedList<TweetData> list = new LinkedList<TweetData>();

		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(ConsumerKey, ConsumerSecret);
		twitter.setOAuthAccessToken(new AccessToken(AccessToken, AccessScret));
		
		try {
			Query query = new Query(searchTerm);
			QueryResult result;

			query.setResultType(Query.ResultType.popular);
			query.setCount(tweetsCount);
			int idx = 1;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				
				for (Status tweet : tweets) {

					/*
					System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
					MediaEntity[] entity = tweet.getMediaEntities();
					if(entity.length!=0){
						System.out.println(entity[0].getMediaURL());
					}
					*/
					
					TweetData temp = new TweetData(idx, tweet.getUser().getScreenName(), 
							tweet.getText(), tweet.getSource());

					if(tweet.getURLEntities().length!=0){
						temp.addTextURL(tweet.getURLEntities()[0].getURL());
					}
					if(tweet.getMediaEntities().length!=0){
						temp.addImageURL(tweet.getMediaEntities()[0].getMediaURL());
					}
					list.add(temp);

					idx++;
				}
			} while ((query = result.nextQuery()) != null);
			
			System.out.println(list);
			
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}

		return list;
	}

	public static void main(String[] args){
	
		TwitterCrawler tc = new TwitterCrawler();
		tc.getTweets("#Singapore", 5);
		
	}

}
