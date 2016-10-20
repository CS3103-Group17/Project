/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

/**
 *
 * @author Christian Morgner
 */
public enum ErrorCategory {
	
	/**
	 * unknown process failure
	 */
	UNKNOWN,

	/**
	 * unidentified error
	 */
	EXCEPTION,

	/**
	 * credit card processing error
	 */
	CREDITCARD,

	/**
	 * user entry error
	 */
	DATA_VALIDATION,

	/**
	 * unauthorized access, unable to authenticate access permissions
	 */
	AUTHENTICATION,
	
	/**
	 * unable to process the request
	 */
	UNABLE_TO_PROCESS_REQUEST,
	
	/**
	 * invalid hotel ID or inactive hotel ID
	 */
	
	INVALID_PROPERTY_ID,
	
	/**
	 * no results
	 */
	RESULT_NULL,
	
	/**
	 * process failure
	 */
	PROCESS_FAIL,
	
	/**
	 * sold out
	 */
	SOLD_OUT,
	
	/**
	 * dates or number of nights do not meet the restricted check in rules
	 */
	RESTRICTED_CHECKIN,
	
	ONEROOM,
	
	/**
	 * supplier communication failure, supplier connectivity issue
	 */
	SUPPLIER_COMMUNICATION,
	
	/**
	 * data parsing issue
	 */
	DATA_PARSE_RESULT,
	
	/**
	 * corporate ID required for rate
	 */
	CORPORATE_RATE,
	
	/**
	 * reservation not found
	 */
	RES_NOT_FOUND,
	
	/**
	 * reservation cancelled
	 */
	RES_CANCELLED,
	
	/**
	 * rate key failed, obtain a new rate key by refreshing the search
	 */
	HRN_QUOTE_KEY_FAILURE,
	
	/**
	 * invalid rate key, obtain a new rate key by refreshing the search
	 */
	HRN_QUOTE_KEY_INVALID,
	
	/**
	 * supplier system offline or down
	 */
	SYS_OFFLINE,
	
	/**
	 * unable to connect to supplier 
	 */
	SUPPLIER_INITITIALIZATION,
	
	/**
	 * unable to connect to supplier 
	 */
	SUPPLIER_ROUTER_EXCEPTION,
	
	/**
	 * reservation exception 
	 */
	EJB_CREATE_EXCEPTION,
	
	/**
	 * supplier exception 
	 */
	FINDER_EXCEPTION,
	
	/**
	 * supplier exception
	 */
	BML_FAIL	,
	
	/**
	 * rate change, obtain a new rate by refreshing the search or selecting a new room or property
	 */
	PRICE_MISMATCH,
	
	/**
	 * incorrect credit card security value
	 */
	CSV_FAIL,
	
	/**
	 * The credit card requires the secure code VBV/3DS credit card process as outlined in the reservation request and test booking procedures.
	 */
	PAYER_AUTH_REQUIRED,
	
	/**
	 * secure code incorrect or secure code process failure
	 */
	PAYER_AUTH_FAILED,
	
	/**
	 * existing itinerary already found in the system
	 */
	ITINERARY_ALREADY_BOOKED
}
