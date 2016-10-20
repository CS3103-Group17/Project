/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia;

import de.morgner.expedia.api.City;
import de.morgner.expedia.api.Country;
import de.morgner.expedia.api.DestinationId;
import de.morgner.expedia.api.DestinationString;
import de.morgner.expedia.api.ExpediaHotel;
import de.morgner.expedia.api.ExpediaHotelInfo;
import de.morgner.expedia.api.HotelIdList;
import de.morgner.expedia.api.HotelListResult;
import de.morgner.expedia.api.Parameter;
import de.morgner.expedia.api.ParameterBase;
import de.morgner.expedia.api.Sort;
import de.morgner.expedia.api.SortKey;
import de.morgner.expedia.rest.ExpediaException;
import de.morgner.expedia.rest.JsonObject;
import de.morgner.expedia.rest.RestClientException;
import de.morgner.expedia.result.ExpediaResult;
import de.morgner.expedia.result.HotelList;
import de.morgner.expedia.result.HotelListResponse;
import de.morgner.expedia.result.HotelRoomAvailabilityResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A JiffyBox API client.
 *
 * @author Christian Morgner
 */
public class ExpediaClient {

	private static final String BASE_URL         = "http://api.ean.com/ean-services/rs";
	private static final int MINOR_REVISION      = 26;
	
	public static final String HOTELS_ROOT       = "/hotel/v3/list";
	
	private long customerId                      = 0L;
	private String userAgent                     = null;
	private String ipAddress                     = null;
	private String apiKey                        = null;
	
	/**
	 * Creates a new Expedia EAN API client with the given customer ID,
	 * API key, customer browser user agent and customer IP address.
	 * 
	 * @param apiKey the API key
	 */
	public ExpediaClient(final long customerId, final String apiKey, final String userAgent, final String ipAddress) {
		this.customerId = customerId;
		this.apiKey     = apiKey;
		this.userAgent  = userAgent;
		this.ipAddress  = ipAddress;
	}

	public ExpediaResult<List<ExpediaHotel>> getHotelsByCountryAndCity(final String countryCode, final String city) throws IOException, RestClientException, ExpediaException {
		return getHotelsByCountryAndCity(countryCode, city, SortKey.CITY_VALUE);
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByCountryAndCity(final String countryCode, final String city, final SortKey sortKey) throws IOException, RestClientException, ExpediaException {
		return extractHotels(JsonObject.doGet(HotelListResponse.class, buildPath("/hotel/v3/list", new City(city), new Country(countryCode), new Sort(sortKey))));
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsById(final Long... hotelIds) throws IOException, RestClientException, ExpediaException {
		return getHotelsById(SortKey.CITY_VALUE, hotelIds);
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsById(final SortKey sortKey, final Long... hotelIds) throws IOException, RestClientException, ExpediaException {
		return extractHotels(JsonObject.doGet(HotelListResponse.class, buildPath("/hotel/v3/list", new HotelIdList(hotelIds), new Sort(sortKey))));
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByFreeTextDestination(final String freeTextDestination) throws IOException, RestClientException, ExpediaException {
		return getHotelsByFreeTextDestination(freeTextDestination, SortKey.CITY_VALUE);
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByFreeTextDestination(final String freeTextDestination, final SortKey sortKey) throws IOException, RestClientException, ExpediaException {
		return extractHotels(JsonObject.doGet(HotelListResponse.class, buildPath("/hotel/v3/list", new DestinationString(freeTextDestination), new Sort(sortKey))));
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByDestinationId(final String destinationId) throws IOException, RestClientException, ExpediaException {
		return getHotelsByDestinationId(destinationId, SortKey.CITY_VALUE);
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByDestinationId(final String destinationId, final SortKey sortKey) throws IOException, RestClientException, ExpediaException {
		return extractHotels(JsonObject.doGet(HotelListResponse.class, buildPath("/hotel/v3/list", new DestinationId(destinationId), new Sort(sortKey))));
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByGeolocation(final double latitude, final double longitude, final int radius) throws IOException, RestClientException, ExpediaException {
		return getHotelsByGeolocation(latitude, longitude, radius, SortKey.PROXIMITY);
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByGeolocation(final double latitude, final double longitude, final int radius, final SortKey sortKey) throws IOException, RestClientException, ExpediaException {

		final HotelListResponse response = JsonObject.doGet(HotelListResponse.class, buildPath("/hotel/v3/list",
			
			new ParameterBase("latitude", Double.valueOf(latitude).toString()),
			new ParameterBase("longitude", Double.valueOf(longitude).toString()),
			new ParameterBase("searchRadius", Integer.valueOf(radius).toString()),
			new ParameterBase("searchRadiusUnit", "KM"),
			new Sort(sortKey)
		));
		
		return extractHotels(response);
	}
	public ExpediaResult<List<ExpediaHotel>> getHotelsByGeolocationNameAndCity(final double latitude, final double longitude, final int radius, final String name, final String city, final String country) throws IOException, RestClientException, ExpediaException {
		return getHotelsByGeolocationNameAndCity(latitude, longitude, radius, name, city, country, SortKey.PROXIMITY);
	}
	
	public ExpediaResult<List<ExpediaHotel>> getHotelsByGeolocationNameAndCity(final double latitude, final double longitude, final int radius, final String name, final String city, final String country, final SortKey sortKey) throws IOException, RestClientException, ExpediaException {
		
		final HotelListResponse response = JsonObject.doGet(HotelListResponse.class, buildPath("/hotel/v3/list",
			
			new ParameterBase("latitude", Double.valueOf(latitude).toString()),
			new ParameterBase("longitude", Double.valueOf(longitude).toString()),
			new ParameterBase("searchRadius", Integer.valueOf(radius).toString()),
			new ParameterBase("searchRadiusUnit", "KM"),
			new ParameterBase("city", city),
			new ParameterBase("countryCode", country),
			new ParameterBase("propertyName", name),
			new Sort(sortKey)
		));
		
		return extractHotels(response);
	}

	public ExpediaResult<ExpediaHotelInfo> getAvailability(final long hotelId, final Date arrivalDate, final Date departureDate) throws IOException, RestClientException, ExpediaException {
		
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		final HotelRoomAvailabilityResponse response = JsonObject.doGet(HotelRoomAvailabilityResponse.class, buildPath("/hotel/v3/avail",
			
			new ParameterBase("hotelId", Long.valueOf(hotelId).toString()),
			new ParameterBase("arrivalDate", dateFormat.format(arrivalDate)),
			new ParameterBase("departureDate", dateFormat.format(departureDate)),
			new ParameterBase("room1", "1")
		));
		
		return null;
	}
	
	// ----- private methods -----
	private ExpediaResult<List<ExpediaHotel>> extractHotels(final HotelListResponse response) {
		
		final ExpediaResult<List<ExpediaHotel>> expediaResult = new ExpediaResult<>();
		
		if (response != null) {

			expediaResult.setError(response.getEanWSError());
			
			final HotelListResult result = response.getHotelListResult();
			if (result != null) {

				// fill fields ins result object
				expediaResult.setCustomerSessionId(result.getCustomerSessionId());
				expediaResult.setCacheKey(result.getCacheKey());
				expediaResult.setCacheLocation(result.getCacheLocation());
				
				final HotelList hotelList = result.getHotelList();
				if (hotelList != null) {
					
					expediaResult.setResult(hotelList.getHotelSummary());
					expediaResult.setHasData(true);
				}
			}
		}
		
		return expediaResult;
	}
	
	
	private String buildPath(final String path, final Parameter... parameters) {
		
		final StringBuilder buf = new StringBuilder(BASE_URL);
		
		buf.append(path);
		buf.append("?cid=").append(customerId);
		buf.append("&apiKey=").append(apiKey);
		buf.append("&minorRev=").append(MINOR_REVISION);
		buf.append("&currencyCode=EUR");
		buf.append("&locale=de_DE");
		buf.append("&customerUserAgent=").append(userAgent);
		buf.append("&customerIpAddress=").append(ipAddress);

		for (final Parameter parameter : parameters) {
			buf.append("&").append(parameter.getKey()).append("=").append(parameter.getValue());
		}
		
		return buf.toString();
	}
}
