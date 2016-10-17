package storage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Cities;
import model.City;

public class storageTest {

	@Test
	public void test() {
		Storage store = new Storage();
		Cities cities = new Cities();
		cities.addCity(new City("Getting Around", "Take the Train"));
		System.out.println(cities.getCities().get(0).getHeaders().get(0));
		store.save(cities);
		
		Cities newCities = store.load();
		assertEquals("Take the Train", newCities.getCities().get(0).getHeaderContent("Getting Around"));
		
		
	}

}
