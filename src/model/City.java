package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class City {
	private ArrayList<String> headers;
	private Map<String, String> headerContents;
	
	public City(){
		headers = new ArrayList<String>();
		headerContents = new HashMap<String, String>();
	}
	
	public City(String headerName, String headerContent){
		headers = new ArrayList<String>();
		headerContents = new HashMap<String, String>();
		addHeader(headerName, headerContent);
	}
	
	public ArrayList<String> getHeaders(){
		return headers;
	}
	
	public String getHeaderContent(String header){
		return headerContents.get(header);
	}
	
	public Map<String, String> getAllHeaderContents(){
		return headerContents;
	}
	
	public void addHeader(String headerName, String headerContent){
		this.headers.add(headerName);
		this.headerContents.put(headerName, headerContent);
	}
}
