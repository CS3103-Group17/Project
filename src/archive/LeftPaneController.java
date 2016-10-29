package archive;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import model.ImageData;
import model.Section;
import model.travels.Cities;
import model.travels.City;

public class LeftPaneController {
	
	private MainViewController mvc;
	
	private StackPane sp;
	private TreeView<String> placeTree;
	private Cities cities;

	public LeftPaneController(MainViewController mvc){
		this.cities = new Cities();
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
        	
        	TreeItem<String> trendItem = new TreeItem<> ("Trend");
            cityItem.getChildren().add(trendItem);
            
        	TreeItem<String> galleryItem = new TreeItem<> ("Gallery");
            cityItem.getChildren().add(galleryItem);
            
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
                    	City c = cities.getCity(treeItem.getParent().getValue());
                    	
                    	String val = treeItem.getValue();
                    	if(val.equals("Trend")){
                    		mvc.loadTrendHTML(c.getGoogleTrendsHTML());
                    	}
                    	else if (val.equals("Gallery")){
                    		mvc.loadGallery(c.getImages());
                    	}
                    	else {
                    		Section s = c.findSectionByName(val);
                        	
                        	mvc.changeCenterContent(s);
                    	}
                    	
                    }
                    else {
                    	City c = cities.getCity(treeItem.getValue());
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
