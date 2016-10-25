package model;

import java.util.ArrayList;
import java.util.UUID;

import model.SearchField;

public class SearchHistory {
    private SearchField searchField;
    private ArrayList<UUID> hotelsIds;
    private ArrayList<UUID> socialsIds;
    private ArrayList<UUID> travelsIds;

	public SearchHistory(SearchField searchField) {
		this.searchField = searchField;
		this.hotelsIds = new ArrayList<UUID>();
		this.socialsIds = new ArrayList<UUID>();
		this.travelsIds = new ArrayList<UUID>();
	}

    public SearchField getSearchField() {
        return searchField;
    }

    public ArrayList<UUID> getHotelsIds() {
        return hotelsIds;
    }

    public void setHotelsIds(ArrayList<UUID> hotelsIds) {
        this.hotelsIds = hotelsIds;
    }

    public void addHotelsId(UUID hotelsId) {
        hotelsIds.add(hotelsId);
    }
    
    public void addHotelsIds(ArrayList<UUID> hotelsIds) {
        this.hotelsIds.addAll(hotelsIds);
    }
    
    public ArrayList<UUID> getSocialsIds() {
        return socialsIds;
    }

    public void setSocialsIds(ArrayList<UUID> socialsIds) {
        this.socialsIds = socialsIds;
    }

    public void addSocialsId(UUID socialsId) {
        socialsIds.add(socialsId);
    }
    
    public void addSocialsIds(ArrayList<UUID> socialsIds) {
        this.socialsIds.addAll(socialsIds);
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
