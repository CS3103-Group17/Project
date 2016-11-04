package model.flights;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Segment {

	private String legID;
	private ArrayList<Integer> segmentIDs;
	private String originID;
	private String destinationID;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private String duration;
	private ArrayList<Integer> stopIDs;
	private ArrayList<Carrier> carriers;
	private String directionality;
	private ArrayList<Flight> flights;
	
	public Segment(String legID, String originID, String destinationID, String departureTime, String arrivalTime, String duration, String directionality){
		this.legID = legID;
		this.segmentIDs = new ArrayList<Integer>();
		this.originID = originID;
		this.destinationID = destinationID;
		if(!departureTime.equals(""))
			this.departureTime = LocalDateTime.parse(departureTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		if(!arrivalTime.equals(""))
			this.arrivalTime = LocalDateTime.parse(arrivalTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		this.duration = duration;
		this.stopIDs = new ArrayList<Integer>();
		this.carriers = new ArrayList<Carrier>();
		this.directionality = directionality;
		this.flights = new ArrayList<Flight>();
	}
	
	public void addSegmentID(int id){
		segmentIDs.add(new Integer(id));
	}
	
	public void addStopID(int id){
		stopIDs.add(new Integer(id));
	}
	
	public void addCarrier(Carrier carrier){
		carriers.add(carrier);
	}
	
	public void addFlight(Flight flight){
		flights.add(flight);
	}
	
	public String getLegID() {
		return legID;
	}
	
	public void setLegID(String legID) {
		this.legID = legID;
	}

	public ArrayList<Integer> getSegmentIDs() {
		return segmentIDs;
	}

	public void setSegmentIDs(ArrayList<Integer> segmentIDs) {
		this.segmentIDs = segmentIDs;
	}

	public String getOriginID() {
		return originID;
	}

	public void setOriginID(String originID) {
		this.originID = originID;
	}

	public String getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public ArrayList<Integer> getStopIDs() {
		return stopIDs;
	}

	public void setStopIDs(ArrayList<Integer> stopIDs) {
		this.stopIDs = stopIDs;
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
}
