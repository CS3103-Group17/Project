/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Christian Morgner
 */
public class Entry {

	@Expose
	private ErrorCategory key = null;
	
	@Expose
	private String value = null;

	public ErrorCategory getKey() {
		return key;
	}

	public void setKey(ErrorCategory key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
