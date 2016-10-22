package main;

import java.util.ArrayList;

import google.GoogleSearch;
import model.City;
import model.Page;
import storage.Storage;
import wikitravel.WikitravelCrawler;

public class DataController {
	
	private Storage s;
	private WikitravelCrawler wc;

	public DataController(Storage s){
		this.s = s;
		wc = new WikitravelCrawler();
	}
	

	public void searchPage(String keyword){
		ArrayList<Page> pages = wc.getSearch(keyword);
		
		for(Page p : pages){
			if(!s.checkIfVisited(p)){
				City c = wc.parsePage(p);
				c.setGoogleTrendsHTML(GoogleSearch.getTrend(p.getPageTitle()));
				c.addImages(GoogleSearch.getImages(p.getPageTitle()));
				s.addCity(c);
				s.addToHistory(p);
			}
			else {
				s.addFromCityToDisplayCity(p);
			}
		}
		
		
		
		s.save();
	}
}
