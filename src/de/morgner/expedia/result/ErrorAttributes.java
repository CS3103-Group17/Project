/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

/**
 *
 * @author Christian Morgner
 */
public enum ErrorAttributes {
	
	CC_TRANSACTIONSTATUS,
	
	TRANSACTIONSTATUSMSG,
	
	/**
	 * VBV / 3DS detail
	 */
	PA_TRANSACTIONSTATUS,
	
	/**
	 * VBV / 3DS detail
	 */
	XID,
	
	/**
	 * VBV / 3DS detail
	 */
	ACS_URL,
	
	/**
	 * VBV / 3DS detail
	 */
	PA_REQ,
	
	/**
	 * VBV / 3DS detail
	 */
	AUTH_CODE,
	
	/**
	 * VBV / 3DS detail
	 */
	PAS_TRANSACTION_ID,
	
	SUPPLIER_ERROR_CODE
}
