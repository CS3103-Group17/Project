/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Christian Morgner
 */
public class ExpediaValueAdd {

	@Expose
	private long id = 0L;
	
	@Expose
	private String description = null;

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

}
