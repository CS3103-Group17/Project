package api.socials;

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
	
	private final String ACCESS_TOKEN = "EAACEdEose0cBAKPQUBcDJL8FlF1GCTrQ17bthU8BcNZCrpjAyegkXli0EXuYsHLwPZC6ZCbWET9XEkZA2l0ZB6CURpVlBN2X8PYdupuSdovNDsZApusn4X0BH39mILZBZBPBtt5OIJohGAieEEx8X3bLRA7f0htHLPEyweoS9u3mkAZDZD";
    private DefaultFacebookClient facebookClient = new DefaultFacebookClient(ACCESS_TOKEN, Version.LATEST);
	
	public ArrayList<FbData> getFbPosts(String searchTerm) {
		ArrayList<FbData> list = new ArrayList<FbData>();
		
		try {
			
			Connection<Page> publicSearch = facebookClient.fetchConnection("search", Page.class, Parameter.with("q", searchTerm), Parameter.with("type", "page"));
			
			int idx = 1;
			for (List<Page> feedConnectionPage : publicSearch) {
				  for (Page page : feedConnectionPage) {
//					  System.out.println("@" + page.getName());
					  Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class, Parameter.with("fields", "message,permalink_url,picture,full_picture"), Parameter.with("limit", 2));
					  for (List<Post> feed: pageFeed) {
						  int i = 1;
						  for (Post post: feed) {
							  FbData temp = new FbData(idx, page.getName(), post.getMessage(), post.getPermalinkUrl(), post.getFullPicture());
//							  System.out.println(post.getMessage());
//							  System.out.println(post.getPicture());
//							  System.out.println(post.getPermalinkUrl());
							  list.add(temp);
							  if (idx == 15) return list;
							  if (i == 2) break;
							  idx++;
							  i++;
						  }
						  if (i == 2) break;
					  }
				  }
			}
			
        } catch (FacebookException ex) {     //So that you can see what went wrong
            ex.printStackTrace(System.err);  //in case you did anything incorrectly
        }
		
		return list;
	}
}
