/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Christian Morgner
 */
public class ExpediaHotel {
	
	/**
	 * ID for the property. This same ID will be used in any subsequent room or reservation requests.
	 */
	@Expose
	private long hotelId = 0L;
	
	@Expose
	private String name = null;
	
	@Expose
	private String address1 = null;
	
	@Expose
	private String city = null;
	
	@Expose
	private String stateProvinceCode = null;
	
	@Expose
	private String countryCode = null;
	
	@Expose
	private String postalCode = null;
	
	@Expose
	private String airportCode = null;
	
	@Expose
	private String supplierType = null;
	
	@Expose
	private double hotelRating = 0.0;
	
	@Expose
	private int confidenceRating = 0;
	
	@Expose
	private long amenityMask = 0L;
	
	@Expose
	private String shortDescription = null;
	
	@Expose
	private String locationDescription = null;
	
	@Expose
	private String lowRate = null;
	
	@Expose
	private String highRate = null;
	
	@Expose
	private String rateCurrencyCode = null;
	
	@Expose
	private double latitude = 0.0;
	
	@Expose
	private double longitude = 0.0;
	
	@Expose
	private double proximityDistance = 0.0;
	
	@Expose
	private String proximityUnit = null;
	
	@Expose
	private boolean hotelInDestination = false;
	
	@Expose
	private String deepLink = null;

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvinceCode() {
		return stateProvinceCode;
	}

	public void setStateProvinceCode(String stateProvinceCode) {
		this.stateProvinceCode = stateProvinceCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public double getHotelRating() {
		return hotelRating;
	}

	public void setHotelRating(double hotelRating) {
		this.hotelRating = hotelRating;
	}

	public int getConfidenceRating() {
		return confidenceRating;
	}

	public void setConfidenceRating(int confidenceRating) {
		this.confidenceRating = confidenceRating;
	}

	public long getAmenityMask() {
		return amenityMask;
	}

	public void setAmenityMask(long amenityMask) {
		this.amenityMask = amenityMask;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getLowRate() {
		return lowRate;
	}

	public void setLowRate(String lowRate) {
		this.lowRate = lowRate;
	}

	public String getHighRate() {
		return highRate;
	}

	public void setHighRate(String highRate) {
		this.highRate = highRate;
	}

	public String getRateCurrencyCode() {
		return rateCurrencyCode;
	}

	public void setRateCurrencyCode(String rateCurrencyCode) {
		this.rateCurrencyCode = rateCurrencyCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getProximityDistance() {
		return proximityDistance;
	}

	public void setProximityDistance(double proximityDistance) {
		this.proximityDistance = proximityDistance;
	}

	public String getProximityUnit() {
		return proximityUnit;
	}

	public void setProximityUnit(String proximityUnit) {
		this.proximityUnit = proximityUnit;
	}

	public boolean isHotelInDestination() {
		return hotelInDestination;
	}

	public void setHotelInDestination(boolean hotelInDestination) {
		this.hotelInDestination = hotelInDestination;
	}

	public String getDeepLink() {
		return deepLink;
	}

	public void setDeepLink(String deepLink) {
		this.deepLink = deepLink;
	}
	
	
}
