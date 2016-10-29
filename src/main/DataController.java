package main;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import model.travels.City;
import storage.PersistentStorage;
import model.SearchHistory;
import model.SearchField;

/* Main purpose of this controller is to handle data in memory. */
public class DataController {
    public static final DataController INSTANCE = new DataController();
    
    /* Data that needs to be cached. */
    private ArrayList<SearchHistory> searchHistories;
    private ArrayList<City> cities;

    /* Poison pill shutdown. */
    private volatile boolean displayQueueFinished;
    private ArrayBlockingQueue<Object> displayQueue;

    private DataController() {
        searchHistories = new ArrayList<SearchHistory>();
        cities = new ArrayList<City>();
        
        displayQueueFinished = true;
        displayQueue = new ArrayBlockingQueue<Object>(1);
    }
    
    public ArrayList<SearchHistory> getSearchHistories() {
        return searchHistories;
    }
    
    public SearchHistory getSearchHistory(SearchField searchField) {
        for (SearchHistory history : searchHistories) {
            if (history.getSearchField().equals(searchField)) {
                return history;
            }
        }
        
        return null;
    }
    
    public void addSearchHistory(SearchHistory searchHistory) {
        searchHistories.add(searchHistory);
    }
    
    public void storeSearchHistory(SearchHistory searchHistory) {
        addSearchHistory(searchHistory);
        
        PersistentStorage.INSTANCE.writeToStorage(searchHistory);
    } 
    
    public ArrayList<City> getCities() {
        return cities;
    }
    
    public City getCity(String name) {
        for (City city: cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        
        return null;
    }
    
    public City getCity(UUID cityId) {
        for (City city : cities) {
            if (city.getCityId().equals(cityId)) {
                return city;
            }
        }
        
        return null;
    }
    
    public void addCity(City city) {
        cities.add(city);
    }    
    
    public void storeCity(City city) {
        addCity(city);
        
        PersistentStorage.INSTANCE.writeToStorage(city);
    }       
    
    public boolean isDisplayQueueFinished() {
        return displayQueueFinished;
    }
    
    public void setDisplayQueueFinished(boolean displayQueueFinished) {
        this.displayQueueFinished = displayQueueFinished;
    }
    
    public ArrayBlockingQueue<Object> getDisplayQueue() {
        return displayQueue;
    }
    
    public void addDisplay(Object object) {
        try {
            displayQueue.put(object);
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
