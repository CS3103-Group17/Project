package archive;

import java.util.ArrayList;

import model.SearchHistory;
import model.Page;
import model.travels.Cities;
import model.travels.City;
import storage.PersistentStorage;

public class Storage {
	//Unsure about the need for history
	private SearchHistory history;
	private Cities cities;
	private Cities displayCities;
	
	private PersistentStorage disk;

	public Storage(){
		//disk = new PersistentStorage();
		displayCities = new Cities();
		//cities = disk.loadCities();
		//history = disk.loadHistory();
	}
	
	public Cities getCities(){
		return cities;
	}
	
	public Cities getDisplayCities(){
		return displayCities;
	}
	
	public void clearDisplayCities(){
		displayCities.clear();
	}
	
	public void addCity(City c){
		cities.addCity(c);
		displayCities.addCity(c);
	}
	
	public void addFromCityToDisplayCity(Page p){
		displayCities.addCity(cities.findCity(p.getPageTitle()));
	}
	
	public void save(){
		disk.save(cities, history);
	}
	
	public void addToHistory(Page p){
		history.addToHistory(p);
	}
	
	public boolean checkIfVisited(Page p){
		return history.checkIfVisited(p);
	}
	
}
