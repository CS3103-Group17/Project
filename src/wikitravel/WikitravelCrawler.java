package wikitravel;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.City;
import model.ImageData;
import model.Page;
import model.Section;
import utils.ContentParser;

public class WikitravelCrawler {
	
	private final int TIMEOUT_DURATION = 0;
	
	public City parsePage(Page page){
		City city = new City(page.getPageTitle());
		String pageTitle = page.getPageTitle().replace(" ", "%20");
		getCitySummary(pageTitle, city);
		ArrayList<Section> sections = getSections(pageTitle);
		for(Section s : sections){
			if(!getSectionContent(pageTitle, s, city))
				sections.remove(s);
		}
		
		city.setSections(sections);
		
		return city;
	}
	
	public ArrayList<Page> getSearch(String keyword){
		ArrayList<Page> pages = new ArrayList<Page>();
		keyword = ContentParser.encodeIntoURL(keyword);
		
		String url = "http://wikitravel.org/wiki/en/api.php?action=opensearch&search="+keyword+"&limit=100&namespace=0&format=xml";
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(TIMEOUT_DURATION)
					  .post();
			
			Iterator<Element> sections = doc.select("Text").iterator();
			while(sections.hasNext()){
				Element pageCon = sections.next();
				Page page = new Page(pageCon.html());
				pages.add(page);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("Couldn't search for keywords");
		}
		
		return pages;
	}
	
	public ArrayList<Section> getSections(String pageTitle){
		ArrayList<Section> sectionsList = new ArrayList<Section>();
		pageTitle = ContentParser.encodeIntoURL(pageTitle);
		
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&prop=sections&page="+pageTitle;
		
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(TIMEOUT_DURATION)
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
	
	public boolean getSectionContent(String pageTitle, Section section, City city){
		pageTitle = ContentParser.encodeIntoURL(pageTitle);
		
		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&page="+pageTitle+"&section="+section.getIndex();
		
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(TIMEOUT_DURATION)
					  .post();
			
			Iterator<Element> sections = doc.select("text").iterator();
			String sectionContent = "";
			while(sections.hasNext()){
				Element sectionCon = sections.next();
				
				if(sectionCon.html().equals(""))
					return false;
				else
					sectionContent = ContentParser.parseSectionContent(sectionCon.html());
			}
			
			section.setContent(sectionContent);
			
			Iterator<Element> images = doc.select("img").iterator();
			while(images.hasNext()){
				Element imageElement = images.next();
				ImageData id = getImageData(imageElement.html());
				
				if(id != null)
					city.addImage(id);
			}
			
			System.out.println(section.getContent());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("Couldn't parse Section");
			return false;
		}
	}
	
	public void getCitySummary(String pageTitle, City city){
		pageTitle = ContentParser.encodeIntoURL(pageTitle);

		String url = "http://wikitravel.org/wiki/en/api.php?format=xml&action=parse&page="+pageTitle+"&section=0";
		
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(TIMEOUT_DURATION)
					  .post();
			
			Iterator<Element> sections = doc.select("text").iterator();
			String sectionContent = "";
			while(sections.hasNext()){
				Element sectionCon = sections.next();
				sectionContent = ContentParser.parseSummaryContent(sectionCon.html());
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
					  .timeout(TIMEOUT_DURATION)
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

	
	private ArrayList<Page> getPagesFromCategory(int numberOfPages, String keyword){
		ArrayList<Page> pages = new ArrayList<Page>();
		
		String url = "http://wikitravel.org/wiki/en/api.php?action=query&list=categorymembers&cmtitle=Category:"+keyword+"&cmlimit="+numberOfPages+"&format=xml";
		Document doc;
		try {
			doc = Jsoup.connect(url)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(TIMEOUT_DURATION)
					  .post();
			
			Iterator<Element> sections = doc.select("cm").iterator();
			while(sections.hasNext()){
				Element pageCon = sections.next();
				Page page = new Page(Integer.parseInt(pageCon.attr("pageid")), pageCon.attr("title"));
				pages.add(page);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("Couldn't parse Page");
		}
		
		return pages;
	}
}
