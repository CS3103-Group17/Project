package model;

import java.util.ArrayList;

public class Cities {
	
	private ArrayList<City> cities;
	
	public Cities(){
		this.cities = new ArrayList<City>();
	}
	
	public ArrayList<City> getCities(){
		return cities;
	}
	
	public void addCity(City city){
		this.cities.add(city);
	}
	
	public City findCity(String name){
		for(City c: cities){
			if(c.getName().equals(name)){
				return c;
			}
		}
		
		return null;
	}
	
	public void clear(){
		cities.clear();
	}
	
}
