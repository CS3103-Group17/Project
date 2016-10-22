package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import model.City;
import model.Page;
import model.Section;
import wikitravel.WikitravelCrawler;

public class WikitravelTest {

	@Test
	public void sectionsTest() {
		WikitravelCrawler wc = new WikitravelCrawler();
		String pageTitle = "San%20Francisco";
		ArrayList<Section> swList = wc.getSections(pageTitle);
		String swResult = "";
		
		for(Section sw : swList){
			swResult = swResult+sw.getName()+"\n";
		}
		String swSample = "Districts\nUnderstand\nHistory\nClimate\nLiterature\nMovies\nTourist information\nTalk"
				+ "\nGet in\nBy plane\nSan Francisco Bay Area Airports\nBy train\nBy bus\nBy boat\nBy car\nGet around"
				+ "\nNavigating\nOn foot\nBy public transit\nBy bike\nBy taxi\nBy car\nRide share programs\nSee\nItineraries"
				+ "\nLandmarks\nNeighborhoods\nMuseums\nParks and outdoors\nDo\nHarbor tours\nPerforming arts\nEvents\nCultural events"
				+ "\nHolidays\nLGBT community events\nOutdoor and recreational events\nSports\nLocal music\nMeet and greet locals"
				+ "\nFree Walking Tours of San Francisco\nBeers and Bike in San Francisco\nLearn\nWork\nBuy\nEat\nDrink\nBars and clubs"
				+ "\nBeer\nSleep\nContact\nStay safe\nRespect\nCope\nPublications\nConsulates\nGet out\n";
		
		assert(swResult.equals(swSample));
		
	}
	
	@Test
	public void sectionContentTest(){
		WikitravelCrawler wc = new WikitravelCrawler();
		String pageTitle = "San%20Francisco";
		Section history = new Section("History", "2.1", 3);
		
		wc.getSectionContent(pageTitle, history, new City("Singapore"));
		
		assert(!history.getContent().equals(""));
	}
	
	@Test
	public void cityTest(){
		WikitravelCrawler wc = new WikitravelCrawler();
		String cityName = "San Francisco";
		City c = wc.parsePage(new Page(0, cityName));
		
		assert(c.getName().equals("San Francisco"));
		assertEquals("Climate", c.getSections().get(3).getName());
	}

}
