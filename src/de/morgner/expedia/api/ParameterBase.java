/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.morgner.expedia.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christian Morgner
 */
public class ParameterBase implements Parameter {

	private String key = null;
	private String value = null;

	public ParameterBase(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(ParameterBase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return value;
	}
}
