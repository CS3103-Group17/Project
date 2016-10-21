package ui;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import model.Cities;
import model.City;
import model.ImageData;
import model.Section;

public class LeftPaneController {
	
	private MainViewController mvc;
	
	private StackPane sp;
	private TreeView<String> placeTree;
	private Cities cities;

	public LeftPaneController(Cities cities, MainViewController mvc){
		this.cities = cities;
		this.mvc = mvc;
		sp = new StackPane();
	}
	
	public StackPane getLeftPane(){
		sp.getChildren().add(placeTree);
		
		return sp;
	}
	
	public void populateTree(){
		TreeItem<String> rootItem = new TreeItem<> ("Places");
		rootItem.setExpanded(true);
        placeTree = new TreeView<> (rootItem);   
		
        for (City city : cities.getCities()) {
        	TreeItem<String> cityItem = new TreeItem<> (city.getName());
        	cityItem.setExpanded(true);
        	for (Section s : city.getSections()) {
                TreeItem<String> sectionItem = new TreeItem<> (s.getName());
                cityItem.getChildren().add(sectionItem);
            } 
        	
        	rootItem.getChildren().add(cityItem);
        }        
        
        placeTree.setCellFactory(tree -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    TreeItem<String> treeItem = cell.getTreeItem();
                    
                    if(treeItem.isLeaf()){
                    	City c = cities.findCity(treeItem.getParent().getValue());
                    	Section s = c.findSection(treeItem.getValue());
                    	
                    	mvc.changeCenterContent(s);
                    }
                    else {
                    	City c = cities.findCity(treeItem.getValue());
                    	if(c != null){
                    		String displayHTML = c.getSummaryContent()+"<br/>";
                    		
                    		for(ImageData id : c.getImages()){
                    			displayHTML = displayHTML+"<img src=\""+id.getURL()+"\"/><br/>";
                    		}
                    		
                    		mvc.changeCenterContent(displayHTML);
                    	}
                    }
                    
                }
            });
            return cell ;
        });
	}
}
