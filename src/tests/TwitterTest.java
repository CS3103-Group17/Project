package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import api.socials.TwitterCrawler;

public class TwitterTest {

	@Test
	public void twitterTest() {
		TwitterCrawler tc = new TwitterCrawler();
		tc.getTweets("#Singapore", 5);
	}

}
