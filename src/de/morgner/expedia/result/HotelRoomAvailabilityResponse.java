/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

import com.google.gson.annotations.Expose;
import de.morgner.expedia.api.ExpediaHotelInfo;
import de.morgner.expedia.rest.JsonObject;

/**
 *
 * @author Christian Morgner
 */
public class HotelRoomAvailabilityResponse extends JsonObject {
	
	@Expose
	private ExpediaHotelInfo HotelRoomAvailabilityResponse = null;
	
	@Expose
	private EanWSError EanWSError = null;

	public ExpediaHotelInfo getExpediaHotelInfo() {
		return HotelRoomAvailabilityResponse;
	}

	public EanWSError getEanWSError() {
		return EanWSError;
	}
}
