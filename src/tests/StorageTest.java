/*package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.SearchHistory;
import model.Page;
import model.Section;
import model.travels.Cities;
import model.travels.City;
import storage.PersistentStorage;

public class StorageTest {

	@Test
	public void citiesStoreTest() {
		PersistentStorage store = new PersistentStorage("test");
		Cities cities = new Cities();
		SearchHistory history = new SearchHistory();
		City c = new City();
		c.addSection(new Section("Getting Around", "1", 1));
		cities.addCity(c);
		
		assertEquals("Getting Around", cities.getCities().get(0).getSections().get(0).getName());
		
		store.save(cities, history);
		
		Cities newCities = store.loadCities();
		assertEquals("Getting Around", newCities.getCities().get(0).getSections().get(0).getName());
		
		store.deleteFile();
	}
	
	@Test
	public void historyStoreTest() {
		PersistentStorage store = new PersistentStorage("test");
		Cities cities = new Cities();
		SearchHistory history = new SearchHistory();
		Page p = new Page(123, "Test Page");
		history.addToHistory(p);
		
		store.save(cities, history);
		
		SearchHistory newHistory = store.loadHistory();
		assert(newHistory.checkIfVisited(p));
		
		store.deleteFile();
	}

}
*/