package wikitravel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.Cities;
import model.Page;
import storage.Storage;

public class WikitravelController {
		private ArrayList<Page> history;
		private Cities cities;
		private WikitravelCrawler wc;
		private Storage s;

		public WikitravelController(){
			history = new ArrayList<Page>();
			cities = new Cities();
			wc = new WikitravelCrawler();
			s = new Storage();
		}
		
		public void parseCategory(){
			ArrayList<Page> pageList = getPages(2);
			
			for(Page p : pageList){
				cities.addCity(wc.parsePage(p));
				history.add(p);
			}
			
			pageList.clear();
			
			s.save(cities);
			
		}
		
		private ArrayList<Page> getPages(int numberOfPages){
			ArrayList<Page> pages = new ArrayList<Page>();
			
			String url = "http://wikitravel.org/wiki/en/api.php?action=query&list=categorymembers&cmtitle=Category:Guide%20articles&cmlimit="+numberOfPages+"&format=xml";
			Document doc;
			try {
				doc = Jsoup.connect(url)
						  .data("query", "Java")
						  .userAgent("Mozilla")
						  .cookie("auth", "token")
						  .timeout(3000)
						  .post();
				
				Iterator<Element> sections = doc.select("cm").iterator();
				while(sections.hasNext()){
					Element pageCon = sections.next();
					Page page = new Page(Integer.parseInt(pageCon.attr("pageid")), pageCon.attr("title"));
					pages.add(page);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println("Couldn't parse Page");
			}
			
			return pages;
		}
		
		public Cities getCities(){
			return cities;
		}
}
