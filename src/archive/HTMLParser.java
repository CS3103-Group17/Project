package archive;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLParser {
	
	DataController dc;
	
	public HTMLParser(DataController dc){
		this.dc = dc;
	}
	
	public boolean crawlHTML(String url){
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
			Iterator<Element> links = doc.select("a").iterator();
			while(links.hasNext()){
				Element link = links.next();
				String linkHref = link.attr("href"); // "http://example.com/"
				
				if(linkHref.contains("http://"))
					dc.addURLToQueue(linkHref);
			}
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't parse URL");
			
			return false;
		}
		
		
	}
}
