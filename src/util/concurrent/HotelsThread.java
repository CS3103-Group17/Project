package util.concurrent;

import java.util.ArrayList;

import api.hotels.ExpediaCrawler;
import main.DataController;
import model.hotels.Hotel;
import util.Constants.Action;
import util.Constants.Hotels;

public class HotelsThread implements Runnable {
    private Hotels hotelsApi;
    private Action action;    

    private CategoryThreadController threadController = CategoryThreadController.INSTANCE;
    private DataController dataController = DataController.INSTANCE;
    
    public HotelsThread(Hotels hotelsApi) {
        this.hotelsApi = hotelsApi;
        this.action = Action.SEARCH;
    }
    
    public HotelsThread(Hotels hotelsApi, Action action) {
        this.hotelsApi = hotelsApi;
        this.action = action;
    }
    
    @Override
    public void run() {
        /* Determine which hotels API to crawl. */
        switch (hotelsApi) {
            default:
                break;
            
            case EXPEDIA:
                crawlExpedia();
                break;
        }
    }
    
    private void crawlExpedia() {
        ExpediaCrawler expediaCrawler = new ExpediaCrawler();
        
        /* Determine what action to perform. */
        switch (action) {
            default:
                break;
                
            case SEARCH:
                ArrayList<Hotel> hotels = expediaCrawler.getHotels(threadController.getCurrentSearchField());
                
                for (Hotel hotel : hotels) {
                    dataController.addDisplay(hotel);
                }
                System.out.println("HERE");
                break;
        }
    }
}
