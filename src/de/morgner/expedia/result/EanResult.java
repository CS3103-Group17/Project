/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

import com.google.gson.annotations.Expose;
import de.morgner.expedia.rest.JsonObject;

/**
 *
 * @author Christian Morgner
 */
public abstract class EanResult extends JsonObject {
	
	@Expose
	private String customerSessionId = null;

	/**
	 * Indicates if there are more property results available to page.
	 * If true, more results can be obtained via the paging process.
	 */
	@Expose
	private boolean moreResultsAvailable = false;
	
	/**
	 * The key to the cache of the current response returned.
	 * Use this value in your next paging request.
	 */
	@Expose
	private String cacheKey = null;
	
	/**
	 * Defines the EAN server location of the cache for the current response returned.
	 * Use this value in your next paging request.
	 */
	@Expose
	private String cacheLocation = null;

	public String getCustomerSessionId() {
		return customerSessionId;
	}

	public void setCustomerSessionId(String customerSessionId) {
		this.customerSessionId = customerSessionId;
	}

	public boolean isMoreResultsAvailable() {
		return moreResultsAvailable;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public String getCacheLocation() {
		return cacheLocation;
	}
	
	
}
