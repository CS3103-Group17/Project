package model;

import java.util.ArrayList;
import java.util.UUID;

import model.SearchField;

/* 
 * This model is used to cache the searches done by the user 
 * and any other static content that was pulled due to the searches. 
 */
public class SearchHistory {
    private SearchField searchField;
    private ArrayList<UUID> travelsIds;

	public SearchHistory(SearchField searchField) {
		this.searchField = searchField;
		this.travelsIds = new ArrayList<UUID>();
	}

    public SearchField getSearchField() {
        return searchField;
    }
    
    public ArrayList<UUID> getTravelsIds() {
        return travelsIds;
    }

    public void setTravelsIds(ArrayList<UUID> travelsIds) {
        this.travelsIds = travelsIds;
    }
    
    public void addTravelsId(UUID travelsId) {
        travelsIds.add(travelsId);
    }
    
    public void addTravelsIds(ArrayList<UUID> travelsIds) {
        this.travelsIds.addAll(travelsIds);
    }
}
