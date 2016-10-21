package wikitravel;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.Page;
import storage.Storage;
import utils.ContentParser;

public class WikitravelController {
		private WikitravelCrawler wc;
		private ContentParser cp;
		private Storage s;
		
		private final int NO_OF_CATEGORY_PAGES = 2;
		
		public WikitravelController(Storage s){
			wc = new WikitravelCrawler();
			cp = new ContentParser();
			this.s = s;
		}
		
		public void searchPage(String keyword){
			ArrayList<Page> pages = getSearch(keyword);
			
			for(Page p : pages){
				if(!s.checkIfVisited(p)){
					s.addCity(wc.parsePage(p));
					s.addToHistory(p);
				}
				else {
					s.addFromCityToDisplayCity(p);
				}
			}
			
			s.save();
		}
		
		public void parseCategory(){
			ArrayList<Page> pageList = getPages(NO_OF_CATEGORY_PAGES);
			
			for(Page p : pageList){
				s.addCity(wc.parsePage(p));
				s.addToHistory(p);
			}
			
			pageList.clear();
			
			s.save();
			
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
				e.printStackTrace();
				
				System.out.println("Couldn't parse Page");
			}
			
			return pages;
		}
		
		private ArrayList<Page> getSearch(String keyword){
			ArrayList<Page> pages = new ArrayList<Page>();
			keyword = cp.parseSearchKeyword(keyword);
			
			String url = "http://wikitravel.org/wiki/en/api.php?action=opensearch&search="+keyword+"&limit=100&namespace=0&format=xml";
			Document doc;
			try {
				doc = Jsoup.connect(url)
						  .data("query", "Java")
						  .userAgent("Mozilla")
						  .cookie("auth", "token")
						  .timeout(3000)
						  .post();
				
				Iterator<Element> sections = doc.select("Text").iterator();
				while(sections.hasNext()){
					Element pageCon = sections.next();
					Page page = new Page(pageCon.html());
					pages.add(page);
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				System.out.println("Couldn't search for keywords");
			}
			
			return pages;
		}
}
