package util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import main.DataController;
import model.flights.Itineraries;
import model.flights.Itinerary;
import model.hotels.Hotel;
import model.socials.FbData;
import model.socials.InstagramData;
import model.socials.Tweet;
import model.travels.City;
import ui.CrawlerUIController;

public class DisplayThread implements Runnable {
    private DataController dataController = DataController.INSTANCE;
    private CrawlerUIController uiController;
    
    public DisplayThread(CrawlerUIController uiController) {
        this.uiController = uiController;
    }
    
    @Override
    public void run() {
        ArrayBlockingQueue<Object> blockingQueue = dataController.getDisplayQueue();
        dataController.setDisplayQueueFinished(false);
        
        while (true) {
            try {
                Object object = blockingQueue.poll(200, TimeUnit.MILLISECONDS);
                
                if (object != null) {
                    if (object instanceof City) {
                        uiController.setCityItem((City) object);
                    } else if (object instanceof Hotel) {
                        uiController.setHotelItem((Hotel) object);
                    } else if ( object instanceof FbData) {
                    	uiController.setSocialItem((FbData) object);
                    } else if (object instanceof InstagramData) {
                        uiController.setSocialItem((InstagramData) object);
                    } else if (object instanceof Itinerary) {
                        uiController.setFlightItem((Itinerary) object);
                    }
                    else if (object instanceof Tweet) {
                        uiController.setSocialItem((Tweet) object);
                    }
                }
                
                if (dataController.isDisplayQueueFinished() && blockingQueue.isEmpty()) {
                    uiController.enableSearch();
                    return;
                }
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                
                uiController.enableSearch();
                return;
            }
        }
    }
}
