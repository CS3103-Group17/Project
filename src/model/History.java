package model;

import java.util.ArrayList;

public class History {
	private ArrayList<Page> history;
	
	public History(){
		this.history = new ArrayList<Page>();
	}
	
	public void addToHistory(Page p){
		history.add(p);
	}
	
	public boolean checkIfVisited(Page p){
		boolean visited = false;
		
		for(Page historyPage: history){
			if(historyPage.getPageID() == -1 || p.getPageID() == -1){
				if(historyPage.getPageTitle().equals(p.getPageTitle())){
					visited = true;
					break;
				}	
			}
			else if(historyPage.getPageID() == p.getPageID()){
				visited = true;
				break;
			}
		}
		
		return visited;
	}
}
