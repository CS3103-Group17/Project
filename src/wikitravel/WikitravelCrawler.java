package wikitravel;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.apache.commons.lang3.StringEscapeUtils;

import model.City;
import model.ImageData;
import model.Page;
import model.Section;
import utils.ContentParser;

public class WikitravelCrawler {

	private ContentParser cp;
	
	public WikitravelCrawler(){
		cp = new ContentParser();
	}
	
	public City parsePage(Page page){
		City city = new City(page.getPageTitle());
		getCitySummaryByPageID(page.getPageID(), city);
		ArrayList<Section> sections = getSectionsByPageID(page.getPageID());
		for(Section s : sections){
			getSectionContentByPageID(page.getPageID(), s);
		}
		
		city.setSections(sections);
		
		return city;
	}
	
	public City parsePage(String pageTitle){
		City city = new City(pageTitle);
		pageTitle = pageTitle.replace(" ", "%20");
		getCitySummaryByPageTitle(pageTitle, city);
		ArrayList<Section> sections = getSectionsByPageTitle(pageTitle);
		for(Section s : sections){
			getSectionContentByPageTitle(pageTitle, s);
		}
		
		city.setSections(sections);
		
		return city;
	}
	
	public ArrayList<Section> getSectionsByPageTitle(String pageTitle){
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&prop=sections&page="+pageTitle;
		return getSections(url);
	}
	
	public ArrayList<Section> getSectionsByPageID(int pageID){
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&prop=sections&pageid="+pageID;
		return getSections(url);
	}
	
	public ArrayList<Section> getSections(String url){
		ArrayList<Section> sectionsList = new ArrayList<Section>();
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
			Iterator<Element> sections = doc.select("s").iterator();
			while(sections.hasNext()){
				Element section = sections.next();
				Section newSection = new Section(section.attr("line"), section.attr("number"), Integer.parseInt(section.attr("index")));
				sectionsList.add(newSection);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't parse Page");
		}

		return sectionsList;
	}
	
	public void getSectionContentByPageTitle(String pageTitle, Section section){
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&page="+pageTitle+"&section="+section.getIndex();
		getSectionContent(url, section);
	}
	
	public void getSectionContentByPageID(int pageID, Section section){
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&pageid="+pageID+"&section="+section.getIndex();
		getSectionContent(url, section);
	}
	
	public void getSectionContent(String url, Section section){
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
			Iterator<Element> sections = doc.select("text").iterator();
			String sectionContent = "";
			while(sections.hasNext()){
				Element sectionCon = sections.next();
				sectionContent = cp.parseSectionContent(sectionCon.html());
			}
			
			section.setContent(sectionContent);
			System.out.println(section.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't parse Section");
		}
	}
	
	public void getCitySummaryByPageTitle(String pageTitle, City city){
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&page="+pageTitle+"&section=0";
		getCitySummary(url, city);
	}
	
	public void getCitySummaryByPageID(int pageID, City city){
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&pageid="+pageID+"&section=0";
		getCitySummary(url, city);
	}
	
	public void getCitySummary(String url, City city){
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
			Iterator<Element> sections = doc.select("text").iterator();
			String sectionContent = "";
			while(sections.hasNext()){
				Element sectionCon = sections.next();
				sectionContent = cp.parseSummaryContent(sectionCon.html());
				System.out.println(sectionContent);
			}
			
			Iterator<Element> images = doc.select("img").iterator();
			while(images.hasNext()){
				Element imageElement = images.next();
				ImageData id = getImageData(imageElement.html());
				
				if(id != null)
					city.addImage(id);
			}
			
			city.setSummaryContent(sectionContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't parse Summary");
		}
	}
	
	private ImageData getImageData(String imageName){
		
		String parsedImageName = imageName.replace(" ", "%20");
		
		if(!imageName.contains("File:"))
			parsedImageName="File:"+parsedImageName;
			
		String url = "http://wikitravel.org/wiki/en/api.php?action=query&titles="+parsedImageName+"&prop=imageinfo&&iiprop=url&iiurlwidth=300&format=xml";
		
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
			Iterator<Element> imageInfos = doc.select("ii").iterator();
			String thumbURL = "";
			while(imageInfos.hasNext()){
				Element imageInfo = imageInfos.next();
				thumbURL = imageInfo.attr("thumburl");
			}
			
			return new ImageData(imageName, thumbURL, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't parse Image");
			
			return null;
		}
	}
}
