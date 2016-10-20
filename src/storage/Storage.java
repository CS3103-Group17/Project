package storage;

import java.util.ArrayList;

import model.Cities;
import model.City;
import model.Page;

public class Storage {
	//Unsure about the need for history
	private ArrayList<Page> history;
	private Cities cities;
	
	private DiskIO disk;

	public Storage(){
		disk = new DiskIO();
		
		cities = disk.load();
		history = new ArrayList<Page>();
	}
	
	public Cities getCities(){
		return cities;
	}
	
	public void addCity(City c){
		cities.addCity(c);
	}
	
	public void save(){
		disk.save(cities);
	}
	
	public void addToHistory(Page p){
		history.add(p);
	}
	
}
