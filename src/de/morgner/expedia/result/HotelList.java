/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

import com.google.gson.annotations.Expose;
import de.morgner.expedia.api.ExpediaHotel;
import de.morgner.expedia.rest.JsonObject;
import java.util.List;

/**
 *
 * @author Christian Morgner
 */
public class HotelList extends JsonObject {
	
	@Expose
	private List<ExpediaHotel> HotelSummary = null;

	public List<ExpediaHotel> getHotelSummary() {
		return HotelSummary;
	}
}
