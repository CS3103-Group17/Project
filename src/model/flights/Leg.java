package model.flights;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Leg {

	private String id;
	private ArrayList<String> segmentIDs;
	private String duration;
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	private ArrayList<Carrier> carriers;
	private String directionality;
	private String originStation;
	private String destinationStation;
	private ArrayList<Flight> flights; 

	
	public Leg(String id, String duration, String departureTime, String arrivalTime, String directionality, String originStation, String destinationStation){
		this.setID(id);
		this.segmentIDs = new ArrayList<String>();
		this.setDuration(duration);
		if(!arrivalTime.equals(""))
			this.setArrivalTime(LocalDateTime.parse(arrivalTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		if(!departureTime.equals(""))
			this.setDepartureTime(LocalDateTime.parse(departureTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		this.setCarriers(new ArrayList<Carrier>());
		this.setDirectionality(directionality);
		this.originStation = originStation;
		this.destinationStation = destinationStation;
		this.setFlights(new ArrayList<Flight>());
	}
	
	public void addSegment(String segmentID){
		segmentIDs.add(segmentID);
	}
	
	public void addCarrier(Carrier carrier){
		carriers.add(carrier);
	}
	
	public void addFlight(Flight flight){
		flights.add(flight);
	}
	
	public ArrayList<String> getSegmentIDs(){
		return segmentIDs;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public ArrayList<Carrier> getCarriers() {
		return carriers;
	}

	public void setCarriers(ArrayList<Carrier> carriers) {
		this.carriers = carriers;
	}

	public String getDirectionality() {
		return directionality;
	}

	public void setDirectionality(String directionality) {
		this.directionality = directionality;
	}

	public ArrayList<Flight> getFlights() {
		return flights;
	}

	public void setFlights(ArrayList<Flight> flights) {
		this.flights = flights;
	}
	
	public String getOriginStation(){
		return originStation;
	}
	
	public String getDestinationStation(){
		return destinationStation;
	}


}
