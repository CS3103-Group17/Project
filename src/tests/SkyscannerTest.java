package tests;

import java.time.LocalDate;

import org.junit.Test;

import api.flights.SkyScannerCrawler;
import model.SearchField;
import model.flights.Itineraries;

public class SkyscannerTest {

	@Test
	public void test() {
		SkyScannerCrawler ss = new SkyScannerCrawler();
		SearchField search = new SearchField("Singapore");
		search.setArrivalDate(LocalDate.parse("2016-11-20"));
		search.setDepartureDate(LocalDate.parse("2016-11-25"));
		search.setPointOfInterest("Kuala Lumpur");
		String url = ss.createFlightSession(search);
		Itineraries itineraries = ss.getFlightDetails(url);
		System.out.println(itineraries.getItineraries().get(0).getInboundLeg().getID());
	}
	

}
