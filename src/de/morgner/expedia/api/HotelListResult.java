/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

import com.google.gson.annotations.Expose;
import de.morgner.expedia.result.EanResult;
import de.morgner.expedia.result.HotelList;
import java.util.List;

/**
 *
 * @author Christian Morgner
 */
public class HotelListResult extends EanResult {

	/**
	 * Confirms the number of rooms originally requested (number of Room nodes)
	 */
	@Expose
	private int numberOfRoomsRequested = 0;
	
	/**
	 * Container for HotelSummary array.
	 * Contains attributes size, to indicate the number of individual properties in the response,
	 * and activePropertyCount, to indicate the total number of active (not necessarily available)
	 * properties in the specified location.
	 */
	@Expose
	private HotelList HotelList = null;

	public int getNumberOfRoomsRequested() {
		return numberOfRoomsRequested;
	}

	public void setNumberOfRoomsRequested(int numberOfRoomsRequested) {
		this.numberOfRoomsRequested = numberOfRoomsRequested;
	}

	public HotelList getHotelList() {
		return HotelList;
	}
}
