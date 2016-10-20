/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

import com.google.gson.annotations.Expose;
import de.morgner.expedia.api.HotelListResult;
import de.morgner.expedia.rest.JsonObject;

/**
 *
 * @author Christian Morgner
 */
public class HotelListResponse extends JsonObject {
	
	@Expose
	private HotelListResult HotelListResponse = null;
	
	@Expose
	private EanWSError EanWSError = null;

	public HotelListResult getHotelListResult() {
		return HotelListResponse;
	}

	public EanWSError getEanWSError() {
		return EanWSError;
	}
}
