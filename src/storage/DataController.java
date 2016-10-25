package storage;

import java.util.ArrayList;
import java.util.UUID;

import model.travels.Cities;
import model.travels.City;
import model.SearchHistory;
import model.SearchField;

/* Main purpose of this controller is to handle data in memory. */
public class DataController {
    public static final DataController INSTANCE = new DataController();
    
    private ArrayList<SearchHistory> searchHistories;
    
    private Cities cities;
    
    private Cities displayCities;
    
    private DataController() {
        searchHistories = new ArrayList<SearchHistory>();
        cities = new Cities();
        displayCities = new Cities();
    }
    
    public void addSearchHistory(SearchHistory searchHistory) {
        searchHistories.add(searchHistory);
    }
    
    public void addCity(City city) {
        cities.addCity(city);
    }
    
    public void addDisplayCity(City city) {
        displayCities.addCity(city);
    }    
    
    public void storeSearchHistory(SearchHistory searchHistory) {
        addSearchHistory(searchHistory);
        
        PersistentStorage.INSTANCE.writeToStorage(searchHistory);
    }
    
    public void storeCity(City city) {
        addCity(city);
        
        PersistentStorage.INSTANCE.writeToStorage(city);
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
    
    public ArrayList<City> getCities() {
        return cities.getCities();
    }
    
    public City getCity(String name) {
        return cities.getCity(name);
    }
    
    public City getCity(UUID cityId) {
        return cities.getCity(cityId);
    }    
    
    public Cities getDisplayCities() {
        return displayCities;
    }
}
