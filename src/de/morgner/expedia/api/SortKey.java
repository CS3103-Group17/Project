/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

/**
 *
 * @author Christian Morgner
 */
public enum SortKey {
	
	/**
	 * Used only in conjunction with hotelIdList. Returns hotels in the exact order listed in the request.
	 */
	NO_SORT,
	
	/**
	 * The default sort order - returns hotels in the same order as if sort is omitted entirely. Properties within the specified city are ordered above properties in surrounding areas.
	 */
	CITY_VALUE,
	
	/**
	 * Places preferred, best-converting properties at the top.
	 */
	OVERALL_VALUE,
	
	/**
	 * Places properties with a promo rate or value add above properties not running promotions.
	 */
	PROMO,
	
	/**
	 * Sorts properties by nightly rate from low to high. The ordering is not perfect due to business/marketing office algorithms applied to property lists accessed by the API. Accurate price sorting is best achieved within your own code after results are received.
	 */
	PRICE,
	
	/**
	 * Sorts properties by nightly rate from high to low. Expect imperfect sort as detailed above.
	 */
	PRICE_REVERSE,
	
	/**
	 * Sorts properties by average nightly rate from low to high. Expect imperfect sort as detailed above.
	 */
	PRICE_AVERAGE,
	
	/**
	 * Sorts by property star rating from high to low.
	 */
	QUALITY,
	
	/**
	 * Sorts by property star rating from low to high.
	 */
	QUALITY_REVERSE,
	
	/**
	 * Sorts properties alphabetically
	 */
	ALPHA,
	
	/**
	 * Sorts based on proximity to the origin point defined via latitude & longitude parameters.
	 */
	PROXIMITY,
	
	/**
	 * Sorts via postal code, from alphanumerically lower codes to higher codes.
	 */
	POSTAL_CODE,
	
	/**
	 * Hotel Collect properties only. Sorts via our collections department's assessment of how likely individual properties are to pay commissions and how quickly they pay them. Best-performing properties return first.
	 */
	CONFIDENCE,
	
	/**
	 * Places Expedia Collect properties first, but sorts Hotel Collect properties thereafter by their confidence level.
	 */
	MARKETING_CONFIDENCE,
		
	/**
	 * If you have an approved TripAdvisor integration, this value will sort results from high to low guest ratings.
	 */
	TRIP_ADVISOR,
}
