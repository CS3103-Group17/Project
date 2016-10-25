package api.socials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import model.socials.InstagramData;

public class InstagramCrawler {
	
	public ArrayList<InstagramData> getIgPosts(String searchTerm){
		
		ArrayList<InstagramData> list = new ArrayList<InstagramData>();
		
		/*
		InstagramService service = new InstagramAuthService()
	            .apiKey("07673c6da6724d95bc141239e8726598")
	            .apiSecret("2f371c2a918d4f53ab7d5329667279ac")
	            .callback("https://github.com/CS3103-Group17").scope("public_content") .build();
		*/
		//String authorizationUrl = service.getAuthorizationUrl();
		//System.out.println(authorizationUrl);
		
		Token access = new Token("4067280484.07673c6.07d12af2564b4db2b1563df3130d2571", "2f371c2a918d4f53ab7d5329667279ac");
		//System.out.println(access.toString());
		Instagram instagram = new Instagram(access);
		
		TagMediaFeed mediaFeed = null;
		try {
			mediaFeed = instagram.getRecentMediaTags(searchTerm);
		} catch (InstagramException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		List<MediaFeedData> mediaFeeds = mediaFeed.getData();
		if(mediaFeeds.isEmpty())return null;
		
		int idx = 1;
		Iterator<MediaFeedData> it = mediaFeeds.iterator();
		while(it.hasNext()){
			MediaFeedData mediaFeedData = it.next();
			InstagramData temp = new InstagramData(idx, mediaFeedData.getUser().getUserName(), 
					mediaFeedData.getCaption().getText(), mediaFeedData.getLink(), 
					mediaFeedData.getImages().getStandardResolution().getImageUrl());
			list.add(temp);
			idx++;
		}
		
		System.out.println(list);
		
		return list;
	}
	
	/*
	public static void main(String args[]){
		InstagramCrawler igCrawler = new InstagramCrawler();
		igCrawler.getIgPosts("Singapore");
	}
	*/
}
