package util.concurrent;

import java.util.ArrayList;
import java.util.UUID;

import api.socials.InstagramCrawler;
import api.socials.TwitterCrawler;
import api.travels.GoogleSearch;
import api.travels.WikitravelCrawler;
import main.DataController;
import model.Page;
import model.SearchField;
import model.SearchHistory;
import model.hotels.Hotel;
import model.socials.InstagramData;
import model.socials.Tweet;
import model.travels.City;
import util.Constants.Action;
import util.Constants.Socials;
import util.Constants.Travels;

public class SocialsThread implements Runnable {
    private Socials socialApi;
    private Action action;
    
    private CategoryThreadController threadController = CategoryThreadController.INSTANCE;
    private DataController dataController = DataController.INSTANCE;
    
    public SocialsThread(Socials socialApi) {
        this.socialApi = socialApi;
        this.action = Action.SEARCH;
    }
    
    public SocialsThread(Socials socialApi, Action action) {
        this.socialApi = socialApi;
        this.action = action;
    }
    
    @Override
    public void run() {
        /* Determine which travels API to crawl. */
        switch (socialApi) {
            default:
                break;
                
            case TWITTER:
                crawlTwitter();
                break;
                
            case INSTAGRAM:
                crawlInstagram();
                break;
        }
    }
    
    private void crawlTwitter() {
        TwitterCrawler twitterCrawler = new TwitterCrawler();
        
        /* Determine what action to perform. */
        switch (action) {
            default:
                break;
                
            case SEARCH:
            	ArrayList<Tweet> tweets = twitterCrawler.getTweets(threadController.getCurrentSearchField().getName(), 15);
                
            	for (Tweet tweet : tweets) {
                    dataController.addDisplay(tweet);
                }

                break;
        }
    }
    
    private void crawlInstagram() {
        InstagramCrawler instaCrawler = new InstagramCrawler();
        
        /* Determine what action to perform. */
        switch (action) {
            default:
                break;
                
            case SEARCH:
            	try{
            		ArrayList<InstagramData> instas = instaCrawler.getIgPosts(threadController.getCurrentSearchField().getName());
                    
                	if(instas != null){
                		for (InstagramData insta : instas) {
                            dataController.addDisplay(insta);
                        }
                	}
            	} catch (Exception e){
            		System.out.println("Instagram loading error");
            	}
            	
            	
                break;
        }
    }
}
