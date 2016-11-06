package model.flights;

import java.util.ArrayList;

public class Itineraries {

	private ArrayList<Carrier> carriers;
	private ArrayList<Itinerary> itineraries;
	private ArrayList<Segment> segments;
	private ArrayList<Leg> legs;
	private ArrayList<Place> places;
	
	public Itineraries(){
		carriers = new ArrayList<Carrier>();
		itineraries = new ArrayList<Itinerary>();
		segments = new ArrayList<Segment>();
		legs = new ArrayList<Leg>();
		setPlaces(new ArrayList<Place>());
	}
	
	public Carrier getCarrier(String carrierID){
		for(Carrier carrier : carriers){
			if(carrier.getID().equals(carrierID))
				return carrier;
		}
		
		return null;
	}
	
	public Segment getSegment(String id){
		for(Segment segment : segments){
			//System.out.println(leg.getLegID()+"   &|| "+ legID);
			if(segment.getLegID().equals(id))
				return segment;
		}
		
		return null;
	}
	
	public Leg getLeg(String legID){
		for(Leg leg : legs){
			//System.out.println(leg.getID()+"   &|| "+ legID);
			if(leg.getID().equals(legID))
				return leg;
		}
		
		return null;
	}
	
	public void addCarrier(Carrier carrier){
		carriers.add(carrier);
	}
	
	public void addItinerary(Itinerary itinerary){
		itineraries.add(itinerary);
	}
	
	public void addSegment(Segment segment){
		segments.add(segment);
	}
	
	public void addPlace(Place place){
		places.add(place);
	}
	
	public void addLeg(Leg leg){
		legs.add(leg);
	}
	
	public ArrayList<Leg> getLegs(){
		return legs;
	}
	
	public ArrayList<Carrier> getCarriers() {
		return carriers;
	}
	
	public void setCarriers(ArrayList<Carrier> carriers) {
		this.carriers = carriers;
	}

	public ArrayList<Itinerary> getItineraries() {
		return itineraries;
	}

	public void setItineraries(ArrayList<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> legs) {
		this.segments = legs;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}
	
}
