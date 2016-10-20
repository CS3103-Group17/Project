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
public class ServerInfo {
	
	@Expose
	private String serverTime = null;
	
	@Expose
	private long timestamp = 0L;
	
	@Expose
	private int instance = 0;

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getInstance() {
		return instance;
	}

	public void setInstance(int instance) {
		this.instance = instance;
	}
}
