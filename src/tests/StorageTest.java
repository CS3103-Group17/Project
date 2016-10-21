package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Cities;
import model.City;
import model.History;
import model.Page;
import model.Section;
import storage.DiskIO;

public class StorageTest {

	@Test
	public void citiesStoreTest() {
		DiskIO store = new DiskIO("test");
		Cities cities = new Cities();
		History history = new History();
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
		DiskIO store = new DiskIO("test");
		Cities cities = new Cities();
		History history = new History();
		Page p = new Page(123, "Test Page");
		history.addToHistory(p);
		
		store.save(cities, history);
		
		History newHistory = store.loadHistory();
		assert(newHistory.checkIfVisited(p));
		
		store.deleteFile();
	}

}
