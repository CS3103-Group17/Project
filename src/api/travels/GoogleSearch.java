package api.travels;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.ImageData;
import util.ContentParser;

public class GoogleSearch {

	public static ArrayList<ImageData> getImages(String keyword){
		ArrayList<ImageData> imageResults = new ArrayList<ImageData>();
		
		String url = "https://www.googleapis.com/customsearch/v1";
		url += "?q="+ContentParser.encodeIntoURL(keyword);
		url += "&cx=011647004059532519852%3A84hkk4xy4eu&output=xml_no_dtd&client=google-csbe";
		url += "&alt=atom&searchType=image&key=AIzaSyAtvvAV6tBzDh7keNa9G1M4rFl1RP4V8PQ";
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					  .data("query", "Java")
					  .cookie("auth", "token")
					  .ignoreContentType(true)
					  .timeout(0)
					  .get();
			
			Iterator<Element> imageInfos = doc.select("entry").iterator();
			while(imageInfos.hasNext()){
				Element imageInfo = imageInfos.next();
				String id = imageInfo.children().select("id").html();
				String title = imageInfo.children().select("title").html();
				title = StringEscapeUtils.unescapeXml(title);
				
				imageResults.add(new ImageData(title, id, ""));
			}
			
			return imageResults;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't search for Images");
			return null;
		}
	}
	
	public static String getTrend(String keyword){
		String url = "https://www.google.com/trends/fetchComponent?hl=en-US";
		url += "&q="+ContentParser.encodeIntoURL(keyword);
		url += "&cid=TIMESERIES_GRAPH_0&export=5";
		
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .userAgent("Mozilla/5.0")
					  .ignoreContentType(true)
					  .timeout(0)
					  .get();
			
			return doc.html();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't search for Trend");
			return "";
		}
	}
}
