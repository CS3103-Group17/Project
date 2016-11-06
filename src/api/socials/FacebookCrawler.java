package api.socials;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.exception.FacebookException;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.Version;

import model.socials.FbData;

public class FacebookCrawler {
	
	private final String ACCESS_TOKEN = "EAACEdEose0cBAMsPIaSMhEvXEdyNZCiiW8gIp70c5Yxzmbp9boPArjrhMROryqIjsyNOBvjCZAktOWBSImsxatqRdgGS2MGuiZAmsViyF6QZAmNERzZBxL66ITOTqG6relvbaqkJnODYQAjLrbJ6aRcNxZB2yUZBLnePckvGrdV4QZDZD";
    private DefaultFacebookClient facebookClient = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);
	
	public ArrayList<FbData> getFbPosts(String searchTerm) {
		ArrayList<FbData> list = new ArrayList<FbData>();
		
		try {
			
			Connection<Page> publicSearch = facebookClient.fetchConnection("search", Page.class, Parameter.with("q", searchTerm), Parameter.with("type", "page"));
			
			int idx = 1;
			for (List<Page> feedConnectionPage : publicSearch) {
				  for (Page page : feedConnectionPage) {
					  System.out.print("@" + page.getName());
					  Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class, Parameter.with("fields", "message,permalink_url,picture,full_picture"));
					  for (List<Post> feed: pageFeed) {
						  for (Post post: feed) {
							  FbData temp = new FbData(idx, page.getName(), post.getMessage(), post.getPermalinkUrl(), post.getPicture());
							  System.out.println(post.getMessage());
							  System.out.println(post.getPicture());
							  System.out.println(post.getPermalinkUrl());
							  list.add(temp);
							  idx++;
						  }
					  }
					  System.out.println();
				  }
			}
			
        } catch (FacebookException ex) {     //So that you can see what went wrong
            ex.printStackTrace(System.err);  //in case you did anything incorrectly
        }
		
		return list;
	}
	
	public void test(String searchTerm) {
		try {
			
			Connection<Page> publicSearch =
					  facebookClient.fetchConnection("search", Page.class,
					    Parameter.with("q", searchTerm), Parameter.with("type", "page"));
			
			for (List<Page> feedConnectionPage : publicSearch) {
				  for (Page i : feedConnectionPage) {
					  Connection<Post> pageFeed = facebookClient.fetchConnection(i.getId() + "/feed", Post.class, Parameter.with("fields", "message,permalink_url,picture,full_picture"));
					  for (List<Post> feed: pageFeed) {
						  for (Post j: feed) {
							  System.out.println(j.getMessage());
							  System.out.println(j.getPicture());
							  System.out.println(j.getPermalinkUrl());
						  }
					  }
					  System.out.println();
				  }
			}
			
        } catch (FacebookException ex) {     //So that you can see what went wrong
            ex.printStackTrace(System.err);  //in case you did anything incorrectly
        }
	}
}