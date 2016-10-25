package model.travels;

import java.util.ArrayList;
import java.util.UUID;

public class Cities {
	private ArrayList<City> cities;
	
	public Cities() {
		this.cities = new ArrayList<City>();
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}
	
    public City getCity(UUID cityId) {
        for (City city : cities) {
            if (city.getCityId().equals(cityId)) {
                return city;
            }
        }
        
        return null;
    }
    
    public City getCity(String name) {
        for (City city: cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        
        return null;
    }
	
    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }
    
    public void addCity(City city) {
        cities.add(city);
    }
    
    public void addCities(ArrayList<City> cities) {
        this.cities.addAll(cities);
    }

    public void clear() {
        cities.clear();
    }    
}
