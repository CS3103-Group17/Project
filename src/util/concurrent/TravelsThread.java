package util.concurrent;

import java.util.ArrayList;
import java.util.UUID;

import api.travels.GoogleSearch;
import api.travels.WikitravelCrawler;
import main.DataController;
import model.Page;
import model.SearchField;
import model.SearchHistory;
import model.travels.City;
import util.Constants.Action;
import util.Constants.Travels;

public class TravelsThread implements Runnable {
    private Travels travelsApi;
    private Action action;
    
    private CategoryThreadController threadController = CategoryThreadController.INSTANCE;
    private DataController dataController = DataController.INSTANCE;
    
    public TravelsThread(Travels travelsApi) {
        this.travelsApi = travelsApi;
        this.action = Action.SEARCH;
    }
    
    public TravelsThread(Travels travelsApi, Action action) {
        this.travelsApi = travelsApi;
        this.action = action;
    }
    
    @Override
    public void run() {
        /* Determine which travels API to crawl. */
        switch (travelsApi) {
            default:
                break;
                
            case WIKITRAVEL:
                crawlWikiTravel();
                break;
        }
    }
    
    private void crawlWikiTravel() {
        WikitravelCrawler wikitravelCrawler = new WikitravelCrawler();
        
        /* Determine what action to perform. */
        switch (action) {
            default:
                break;
                
            case SEARCH:
                SearchField searchField = threadController.getCurrentSearchField();
                SearchHistory searchHistory = dataController.getSearchHistory(searchField);
                
                /* If similar search have been done before. */
                if (searchHistory != null) {
                    ArrayList<UUID> travelsIds = searchHistory.getTravelsIds();
                
                    for (UUID travelsId : travelsIds) {
                        City city = dataController.getCity(travelsId);
                        
                        if (city != null) {
                            dataController.addDisplay(city);
                        }
                    }
                
                /* If not, crawl the web. */
                } else {
                    ArrayList<Page> pages = wikitravelCrawler.getSearch(searchField.getName());
                    //System.out.println(searchField.getName());
                    
                    for (Page page : pages) {
                        String name = page.getPageTitle();
                        System.out.println(name);
                        
                        City city = dataController.getCity(name);
                        
                        /* City have not been crawled before. */
                        if (city == null) {
                            city = wikitravelCrawler.parsePage(page);
                            city.setGoogleTrendsHTML(GoogleSearch.getTrend(name));
                            city.addImages(GoogleSearch.getImages(name));
                            
                            dataController.storeCity(city);
                        }

                        dataController.addDisplay(city);
                        
                        threadController.getCurrentSearchHistory().addTravelsId(city.getCityId());
                    }
                }

                break;
        }
    }
}
