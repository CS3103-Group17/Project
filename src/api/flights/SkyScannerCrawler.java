package api.flights;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import model.SearchField;
import model.flights.Carrier;
import model.flights.Flight;
import model.flights.Itineraries;
import model.flights.Itinerary;
import model.flights.Leg;
import model.flights.Place;
import model.flights.Segment;
import util.ContentParser;

public class SkyScannerCrawler {

	public final static String apiKey = "prtl6749387986743898559646983194";
	public final static String market = "SG";
	public final static String currency = "SGD";
	public final static String locale = "en-SG";
	public final static String originPlace = "EDI"; //ID of location for auto suggest
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
	
	public String createFlightSession(SearchField search){
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.post("http://partners.api.skyscanner.net/apiservices/pricing/v1.0").
			        header("accept",  "application/json").
			        header("content-type", "application/x-www-form-urlencoded").
			        queryString("apiKey", apiKey).
			        field("country", market).
			        field("currency", currency).
			        field("locale", locale).
			        field("originplace", getPlaceID(search.getPointOfInterest())).
			        field("destinationplace", getPlaceID(search.getName())).
			        field("outbounddate", search.getArrivalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).
			        field("inbounddate", search.getDepartureDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).
			        field("locationschema", "Sky").
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
	
	public String getPlaceID(String searchTerm){
		String url = "http://partners.api.skyscanner.net/apiservices/autosuggest/v1.0/"+market+"/"+currency+"/"+locale;
		String placeID = null;
		try {
			HttpResponse<JsonNode> response = Unirest.get(url).
			        header("accept",  "application/json").
			        header("content-type", "application/x-www-form-urlencoded").
			        queryString("apiKey", apiKey).
			        queryString("query", searchTerm).
			        asJson();
			
			JSONObject json = new JSONObject(response.getBody().toString());
			Document doc = Jsoup.parse(XML.toString(json), "", Parser.xmlParser());
			
			Iterator<Element> placesElements = doc.select("places").iterator();
			Element place = placesElements.next();
			placeID = place.select("placeid").html();
			
			System.out.println(placeID);
			
			return placeID;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return placeID;
		}
		
		
	}
	
	public Itineraries getFlightDetails(String url){
		Itineraries itineraries = new Itineraries();
		try {
			//System.out.println(url);
			HttpResponse<JsonNode> response = Unirest.get(url).
			        asJson();
			
			int counter = 0;
			
			while(response.getBody() == null){
				response = Unirest.get(url).
				        asJson();
				
				counter++;
				
				if(counter > 100){
					throw new Exception();
				}
			}
			
			JSONObject json = new JSONObject(response.getBody().toString());
			Document doc = Jsoup.parse(XML.toString(json), "", Parser.xmlParser());
			
			//System.out.println(doc.select("legs").html());
			
			Iterator<Element> carriersElements = doc.select("carriers").iterator();
			while(carriersElements.hasNext()){
				Element carrier = carriersElements.next();
				String id = carrier.select("id").html();
				String imageURL = carrier.select("imageurl").html();
				String code = carrier.select("code").html();
				String displayCode = carrier.select("displaycode").html();
				String name = carrier.select("name").html();
				itineraries.addCarrier(new Carrier(id, code, name, imageURL, displayCode));
			}
			
			Iterator<Element> placesElements = doc.select("places").iterator();
			while(placesElements.hasNext()){
				Element place = placesElements.next();
				String id = place.select("id").html();
				String parentID = place.select("parentid").html();
				String code = place.select("code").html();
				String type = place.select("type").html();
				String name = place.select("name").html();
				itineraries.addPlace(new Place(id, parentID, code, type, name));
			}
			
			Iterator<Element> segmentsElements = doc.select("segments").iterator();
			while(segmentsElements.hasNext()){
				Element segment = segmentsElements.next();
				String directionality = segment.select("directionality").html();
				String originStation = segment.select("originstation").html();
				String departureDateTime = segment.select("departuredatetime").html();
				String arrivalDateTime = segment.select("arrivaldatetime").html();
				String destinationStation = segment.select("destinationstation").html();
				String operatingCarrier = segment.select("operatingcarrier").html();
				String flightNumber = segment.select("flightnumber").html();
				String duration = segment.select("duration").html();
				String id = segment.select("id").html();
				String carrierID = segment.select("carrier").html();
				Segment newSegment = new Segment(id, originStation, destinationStation, departureDateTime, arrivalDateTime, duration, directionality);
				Carrier carrier = itineraries.getCarrier(carrierID);
				newSegment.addCarrier(carrier);
				newSegment.addFlight(new Flight(flightNumber, carrier));
				itineraries.addSegment(newSegment);
			}
			
			Iterator<Element> legsElements = doc.select("legs").iterator();
			while(legsElements.hasNext()){
				Element leg = legsElements.next();
				String directionality = leg.select("directionality").html();
				String originStation = leg.select("originstation").html();
				String departureDateTime = leg.select("departure").html();
				String arrivalDateTime = leg.select("arrival").html();
				String destinationStation = leg.select("destinationstation").html();
				String flightNumber = leg.select("flightnumber").html();
				String duration = leg.select("duration").html();
				String id = leg.select("id").html();
				String carrierID = leg.select("carrierid").html();
				Leg newLeg = new Leg(id, duration, departureDateTime, arrivalDateTime, directionality, originStation, destinationStation);
				Carrier carrier = itineraries.getCarrier(carrierID);
				newLeg.addCarrier(carrier);
				newLeg.addFlight(new Flight(flightNumber, carrier));
				itineraries.addLeg(newLeg);
			}
			
			Iterator<Element> itineraryElements = doc.select("itineraries").iterator();
			while(itineraryElements.hasNext()){
				Element itinerary = itineraryElements.next();
				String outboundLegID = itinerary.select("outboundlegid").html();
				String inboundLegID = itinerary.select("inboundlegid").html();
				String price = itinerary.select("price").first().html();
				Itinerary newItinerary = new Itinerary(outboundLegID, inboundLegID, price);
				newItinerary.setOutboundLeg(itineraries.getLeg(outboundLegID));
				newItinerary.setInboundLeg(itineraries.getLeg(inboundLegID));
				itineraries.addItinerary(newItinerary);
			}
			
			return itineraries;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Failed to get Flights");
			return null;
		}
	}
}
