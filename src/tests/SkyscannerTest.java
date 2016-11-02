package tests;

import org.junit.Test;

import api.flights.SkyScannerCrawler;

public class SkyscannerTest {

	@Test
	public void test() {
		SkyScannerCrawler ss = new SkyScannerCrawler();
		String test = ss.createFlightSession();
		ss.getFlightDetails(test);
	}
	

}
