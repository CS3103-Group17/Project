package util.concurrent;

import api.flights.SkyScannerCrawler;
import main.DataController;
import model.SearchField;
import model.flights.Itineraries;
import model.flights.Itinerary;
import util.Constants.Action;
import util.Constants.Flights;

public class FlightsThread implements Runnable {
    private Flights flightsApi;
    private Action action;
    private SearchField search;
    
    private CategoryThreadController threadController = CategoryThreadController.INSTANCE;
    private DataController dataController = DataController.INSTANCE;
    
    public FlightsThread(Flights flightsApi) {
        this.flightsApi = flightsApi;
        this.action = Action.SEARCH;
    }
    
    public FlightsThread(Flights flightsApi, Action action) {
        this.flightsApi = flightsApi;
        this.action = action;
    }
    
    public FlightsThread(Flights flightsApi, Action action, SearchField search) {
        this.flightsApi = flightsApi;
        this.action = action;
        this.search = search;
    }
    
    @Override
    public void run() {
        /* Determine which travels API to crawl. */
    	if(search.getArrivalDate() != null && search.getDepartureDate() != null && !search.getPointOfInterest().equals("")){
    		switch (flightsApi) {
            default:
                break;
                
            case SKYSCANNER:
            	crawlSkyScanner();
                break;
    		}
    	}
        
    }
    
    private void crawlSkyScanner() {
        SkyScannerCrawler skyscannerCrawler = new SkyScannerCrawler();
        
        /* Determine what action to perform. */
        switch (action) {
            default:
                break;
                
            case SEARCH:
            	String url = skyscannerCrawler.createFlightSession(search);
            	Itineraries itineraries = skyscannerCrawler.getFlightDetails(url);
                
            	for (Itinerary itinerary : itineraries.getItineraries()) {
                    dataController.addDisplay(itinerary);
                }

                break;
        }
    }
}
