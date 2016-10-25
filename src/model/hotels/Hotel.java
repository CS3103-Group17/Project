package model.hotels;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Hotel {

	
	private String name;
	private String address1;
	private float totalPrice;
	private String rateCurrencyCode;
	private String roomDescription;
	NumberFormat formatter = new DecimalFormat("#0.00");
	
	//not used atm
	private int hotelId;
	private float highRate;
	private float lowRate;
	private String city;
	private String locationDescription;
	private String countryCode;
	private int maxRoomOccupancy;
	private int quotedRoomOccupancy;
	private String shortDescription;
	
	public Hotel(String name, String address1, 
			float totalPrice, String rateCurrencyCode, String roomDescription) {

		this.name  = name;
		this.name  = name;
		this.address1 = address1;
		this.roomDescription = roomDescription;
		this.totalPrice  = totalPrice;
		this.rateCurrencyCode  = rateCurrencyCode;
	}
	
	/*
	public HotelData(String name, String address1, 
			float highRate, float lowRate, String rateCurrencyCode, String roomDescription) {

		this.name  = name;
		this.name  = name;
		this.address1 = address1;
		this.roomDescription = roomDescription;
		this.highRate  = highRate;
		this.lowRate = lowRate;
		this.rateCurrencyCode  = rateCurrencyCode;
	}
	
	public HotelData(int hotelId, String name, String city, String locationDescription, String countryCode, String shortDescription,
			int maxRoomOccupancy, String roomDescription, 
			int quotedRoomOccupancy, String address1, 
			float highRate, float lowRate, String rateCurrencyCode) {
		this.hotelId = hotelId;
		this.name  = name;
		this.city = city;
		this.name  = name;
		this.locationDescription = locationDescription;
		this.countryCode  = countryCode;
		this.shortDescription = shortDescription;
		this.maxRoomOccupancy  = maxRoomOccupancy;
		
		this.quotedRoomOccupancy  = quotedRoomOccupancy;
		this.address1 = address1;
		this.highRate  = highRate;
		this.lowRate = lowRate;
		this.rateCurrencyCode  = rateCurrencyCode;
	}
	*/

	public int gethotelId() {
		return hotelId;
	}
	public int getmaxRoomOccupancy() {
		return maxRoomOccupancy;
	}
	public int getquotedRoomOccupancy() {
		return quotedRoomOccupancy;
	}
	public float gethighRate() {
		return highRate;
	}
	public float getlowRate() {
		return lowRate;
	}
	public float getavgRate() {
		return Float.valueOf(formatter.format((highRate+lowRate)/2));
	}
	public String getname() {
		return name;
	}
	public String getcity() {
		return city;
	}
	public String getlocationDescription() {
		return locationDescription;
	}
	public String getcountryCode() {
		return countryCode;
	}
	public String getroomDescription() {
		return roomDescription;
	}
	public String getaddress() {
		return address1;
	}
	public String getrateCurrencyCode() {
		return rateCurrencyCode;
	}
	public String getshortDescription() {
		return shortDescription;
	}


	public String toString(){
		return "Name: "+name +"\nAddress: "+ address1  +"\nTotal Price: "+ totalPrice  +rateCurrencyCode+"\nRoom Description: "+roomDescription+"\n";
		//return "Name: "+name +"\nAddress: "+ address1  +"\nHigh rate: "+ highRate  +rateCurrencyCode+"\nLow rate: "+ lowRate +rateCurrencyCode+"\nRoom Description: "+roomDescription+"\n";
		/*
		return "#"+hotelId+" "+", "+name+" @"+city+","+countryCode+", "+address1+"\n"
				+locationDescription+", "+shortDescription+ "\n"
				+"roomDesc:" +roomDescription+", maxRoom: "+maxRoomOccupancy+"\n"
				+"quotedPax: "+quotedRoomOccupancy+ ", rate:" +getavgRate() +" " +rateCurrencyCode + "\n";
		*/
		
	}
	
}
