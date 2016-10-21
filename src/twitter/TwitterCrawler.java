package twitter;

import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class twitterCrawler {
	public final static String ConsumerKey = "splkcDS1sIUD73XoxojCUkAZz";
	public final static String ConsumerSecret = "pwClsMLOBnMIGeRJXvmKUW5aFkCTOcnWo1jgN2xoIdym2dfOD5";
	public final static String AccessToken = "785738937620803588-lmCTgOlXycaJaflvmfq9D8zH67nHsFe";
	public final static String AccessScret = "PPhb08AChoiwjoXTt36mNNjDy3YeQWA6gsPIUx7z03cnj";
	
	public static void main(String[] args){
		
		Twitter twit = new TwitterFactory().getInstance();
		
		twit.setOAuthConsumer(ConsumerKey, ConsumerSecret);
        twit.setOAuthAccessToken(new AccessToken(AccessToken, AccessScret));
		
		try {
            Query query = new Query("#Singapore");
            QueryResult result;
            
            query.setResultType(Query.ResultType.popular);
            query.setCount(5);
            do {
                result = twit.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    MediaEntity[] entity = tweet.getMediaEntities();
                    if(entity.length!=0){
                    	System.out.println(entity[0].getMediaURL());
                    }
                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }		
	}
	
}
