package main;

public class MainController {
	
	DataController dc;
	HTMLParser hp;
	
	public MainController(){
		dc = new DataController();
		hp = new HTMLParser(dc);
	}
	
	public void addURL(String url){
		dc.addURLToQueue(url);
	}
	
	public void runCrawler(){
		while(!dc.urlQueueIsEmpty()){
			String url = dc.getNextURL();
			System.out.println(url);
			
			long startTime = System.nanoTime();
			boolean success = hp.crawlHTML(url);
			long endTime = System.nanoTime();
			
			if(success){
				dc.addToHistory(url, endTime - startTime);
			}
		}
	}
}
