/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.result;

import com.google.gson.annotations.Expose;
import java.util.Map;

/**
 *
 * @author Christian Morgner
 */
public class EanWSError {

	@Expose
	private long itineraryId = 0L;
	
	@Expose
	private int exceptionConditionId = 0;
	
	@Expose
	private String presentationMessage = null;
	
	@Expose
	private String verboseMessage = null;
	
	@Expose
	private ErrorHandling errorHandling = null;
	
	@Expose
	private ErrorCategory errorCategory = null;
	
	@Expose
	private Map<ErrorAttributes, String> ErrorAttributes = null;
	
	@Expose
	private ServerInfo ServerInfo = null;

	public long getItineraryId() {
		return itineraryId;
	}

	public void setItineraryId(long itineraryId) {
		this.itineraryId = itineraryId;
	}

	public int getExceptionConditionId() {
		return exceptionConditionId;
	}

	public void setExceptionConditionId(int exceptionConditionId) {
		this.exceptionConditionId = exceptionConditionId;
	}

	public String getPresentationMessage() {
		return presentationMessage;
	}

	public void setPresentationMessage(String presentationMessage) {
		this.presentationMessage = presentationMessage;
	}

	public String getVerboseMessage() {
		return verboseMessage;
	}

	public void setVerboseMessage(String verboseMessage) {
		this.verboseMessage = verboseMessage;
	}

	public ErrorHandling getErrorHandling() {
		return errorHandling;
	}

	public void setErrorHandling(ErrorHandling errorHandling) {
		this.errorHandling = errorHandling;
	}

	public ErrorCategory getErrorCategory() {
		return errorCategory;
	}

	public void setErrorCategory(ErrorCategory errorCategory) {
		this.errorCategory = errorCategory;
	}

	public Map<ErrorAttributes, String> getErrorAttributes() {
		return ErrorAttributes;
	}

	public void setErrorAttributes(Map<ErrorAttributes, String> ErrorAttributes) {
		this.ErrorAttributes = ErrorAttributes;
	}

	public ServerInfo getServerInfo() {
		return ServerInfo;
	}

	public void setServerInfo(ServerInfo ServerInfo) {
		this.ServerInfo = ServerInfo;
	}
}
