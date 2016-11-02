package api.flights;

import org.jsoup.nodes.Document;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class SkyScannerCrawler {

	public final static String apiKey = "prtl6749387986743898559646983194";
	public final static String market = "UK";
	public final static String currency = "GBP";
	public final static String locale = "en-GB";
	public final static String originPlace = "EDA"; //ID of location for auto suggest
	public final static String destinationplace = "LHR";
	public final static String outbounddate = "2016-11-09";
	
	public String createFlightSession(){
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.post("http://partners.api.skyscanner.net/apiservices/pricing/v1.0").
			        header("accept",  "application/json").
			        header("content-type", "application/x-www-form-urlencoded").
			        queryString("apiKey", apiKey).
			        field("country", market).
			        field("currency", currency).
			        field("locale", locale).
			        field("originPlace", originPlace).
			        field("destinationplace", destinationplace).
			        field("outbounddate", outbounddate).
			        field("locationschema", "Iata").
			        asJson();
			
			String url = response.getHeaders().get("Location").toString();
			url = url.substring(1, url.length()-1)+"?apiKey="+apiKey;
			
			return url;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void getFlightDetails(String url){
		try {
			HttpResponse<JsonNode> response = Unirest.get(url).
			        asJson();
			
			//TODO: Parse JSON File
			
			System.out.println(response.getBody().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
