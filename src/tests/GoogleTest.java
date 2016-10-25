package tests;
import java.util.ArrayList;

import org.junit.Test;

import api.travels.GoogleSearch;
import model.ImageData;

public class GoogleTest {

	@Test
	public void googleImagesTest(){
		String s = "Singapore";
		
		ArrayList<ImageData> imgData = GoogleSearch.getImages(s);
			
		assert((imgData.get(0) != null));
	}
	
	@Test
	public void googleTrendTest(){
		String s = "Singapore";
		
		String trendHTML = GoogleSearch.getTrend(s);
			
		System.out.println(trendHTML);
	}
	
}
