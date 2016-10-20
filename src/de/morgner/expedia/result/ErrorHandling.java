/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

/**
 *
 * @author Christian Morgner
 */
public enum ErrorHandling {

	/**
	 * A successful booking could not be placed and a reason for the failure is unknown.
	 */
	UNKNOWN,
	
	/**
	 * This usually indicates a user error, such as incorrect entries that won’t pass validation.
	 * To recover, simply have the user correct the entry error for the field as indicated in the
	 * message. This could also be a recoverable error by resubmitting the booking with refreshed
	 * values.
	 */
	RECOVERABLE,

	/**
	 * The error that occurred cannot be corrected by either the user or a customer service agent.
	 * To place this booking, the user must go back and restart or refresh the search. This error
	 * occurs with invalid request issues, unusable data, or supplier problems.
	 */
	UNRECOVERABLE,
	
	/**
	 * We were able to record the itinerary, however, due to the nature of the error or the booking attempt.
	 * - an agent must follow up to complete the booking manually
	 * - verify a booking that has been flagged for fraud
	 * - return the confirmation number to the user when the Hotel
	 *   Collect property returns one (if not returned with confirmation
	 *   automatically)
	 * - respond to the user that an error occurred and ask if they would
	 *   like to follow through with the booking request. In many cases,
	 *   the user may be referred back to the website to try the booking
	 *   again online.
	 */
	AGENT_ATTENTION
}
