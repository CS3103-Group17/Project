/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 *
 * @author Christian Morgner
 */
public class ExpediaHotelInfo {

	@Expose
	private long hotelId = 0L;
	
	@Expose
	private String arrivalDate = null;
	
	@Expose
	private String departureDate = null;
	
	@Expose
	private String hotelName = null;
	
	@Expose
	private String hotelAddress = null;
	
	@Expose
	private String hotelCity = null;
	
	@Expose
	private String hotelStateProvince = null;
	
	@Expose
	private String hotelCountry = null;
	
	@Expose
	private int numberOfRoomsRequested = 0;
	
	@Expose
	private String checkInInstructions = null;
	
	@Expose
	private List<ExpediaHotelRoom> HotelRoomResponse = null;

	public long getHotelId() {
		return hotelId;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public String getHotelCity() {
		return hotelCity;
	}

	public String getHotelStateProvince() {
		return hotelStateProvince;
	}

	public String getHotelCountry() {
		return hotelCountry;
	}

	public int getNumberOfRoomsRequested() {
		return numberOfRoomsRequested;
	}

	public String getCheckInInstructions() {
		return checkInInstructions;
	}

	public List<ExpediaHotelRoom> getHotelRoomResponse() {
		return HotelRoomResponse;
	}
}
