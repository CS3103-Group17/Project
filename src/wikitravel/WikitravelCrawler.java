package wikitravel;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.apache.commons.lang3.StringEscapeUtils;

import model.City;
import model.Page;
import model.Section;

public class WikitravelCrawler {

	public WikitravelCrawler(){
		
	}
	
	public City parsePage(Page page){
		City city = new City(page.getPageTitle());
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
				sectionContent = sectionCon.html();
			}
			
			section.setContent(StringEscapeUtils.unescapeXml(sectionContent));
			System.out.println(section.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Couldn't parse Section");
		}
	}
}
