package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Cities;
import model.City;
import model.Section;
import storage.Storage;

public class StorageTest {

	@Test
	public void test() {
		Storage store = new Storage();
		Cities cities = new Cities();
		City c = new City();
		c.addSection(new Section("Getting Around", "1", 1));
		cities.addCity(c);
		
		store.save(cities);
		
		Cities newCities = store.load();
		assertEquals("Getting Around", newCities.getCities().get(0).getSections().get(0).getName());
		
		store.deleteFile();
	}

}
