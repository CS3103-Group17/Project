/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

/**
 * A collection of the various response elements of an EAN response
 * on the different JSON levels merged into one object for convenience.
 *
 * @author Christian Morgner
 */
public class ExpediaResult<T> {

	private String customerSessionId = null;
	private String cacheLocation     = null;
	private String cacheKey          = null;
	private EanWSError error         = null;
	private T result                 = null;
	private boolean hasData          = false;

	public ExpediaResult() {
	}
	
	public ExpediaResult(final String customerSessionId, final T result) {

		this.customerSessionId = customerSessionId;
		this.result            = result;
	}

	public String getCustomerSessionId() {
		return customerSessionId;
	}

	public T getResult() {
		return result;
	}

	public void setCustomerSessionId(String customerSessionId) {
		this.customerSessionId = customerSessionId;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getCacheLocation() {
		return cacheLocation;
	}

	public void setCacheLocation(String cacheLocation) {
		this.cacheLocation = cacheLocation;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public EanWSError getError() {
		return error;
	}

	public void setError(EanWSError error) {
		this.error = error;
	}
	
	public boolean hasData() {
		return hasData;
	}
	
	public void setHasData(final boolean hasData) {
		this.hasData = hasData;
	}
}
