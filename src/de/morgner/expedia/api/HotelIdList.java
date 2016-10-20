/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

/**
 *
 * @author Christian Morgner
 */
public class HotelIdList extends ParameterBase {

	public HotelIdList(final Long... ids) {
		super("hotelIdList", org.apache.commons.lang3.StringUtils.join(ids, ","));
	}
}
