package ui;

import com.sun.glass.events.MouseEvent;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Cities;
import model.City;
import model.ImageData;
import model.Section;
import wikitravel.WikitravelController;

public class MainViewController {

	private BorderPane mainPane;
	private StackPane topPane;
	private StackPane leftPane;
	private ScrollPane centerPane;
	
	private WikitravelController wtCon;
	private WebEngine centerWebEngine;
	
	private final double WINDOW_WIDTH = 1000.0;
	private final double WINDOW_HEIGHT = 600.0;
	
	private Cities cities;

	// @@author A0139963N
	public MainViewController() {
		wtCon = new WikitravelController();
		wtCon.parseCategory();
		cities = wtCon.getCities();
	}

	public BorderPane initialize(Stage primaryStage) {
		try {
			setUpMainView(primaryStage);
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return mainPane;
	}

	private void setUpMainView(Stage primaryStage) {
		setUpMainPane();
		setUpLeftPane();
		setUpTopPane();
		setUpCentrePane();
	}

	private void setUpMainPane() {
		mainPane = new BorderPane();
		mainPane.getStyleClass().add("pane");
		mainPane.setId("mainPane");
		mainPane.setPrefWidth(WINDOW_WIDTH);
		mainPane.setPrefHeight(WINDOW_HEIGHT);
		mainPane.setPadding(new Insets(14.0));
	}
	
	private void setUpCentrePane(){
		
		WebView webView = new WebView();
        centerWebEngine = webView.getEngine();
        
		mainPane.setCenter(webView);
	}
	
	private void setUpTopPane(){
		topPane = new StackPane();
		topPane.setPadding(new Insets(14.0));
		
		TextField tf = new TextField();
		topPane.getChildren().add(tf);
		
		mainPane.setTop(topPane);
	}
	
	private void setUpLeftPane(){     
		TreeItem<String> rootItem = new TreeItem<> ("Places");
		rootItem.setExpanded(true);
        TreeView<String> placeTree = new TreeView<> (rootItem);   
		
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
                    	
                    	changeCenterContent(s);
                    }
                    else {
                    	City c = cities.findCity(treeItem.getValue());
                    	if(c != null){
                    		String displayHTML = c.getSummaryContent()+"<br/>";
                    		
                    		for(ImageData id : c.getImages()){
                    			displayHTML = displayHTML+"<img src=\""+id.getURL()+"\"/><br/>";
                    		}
                    		
                    		changeCenterContent(displayHTML);
                    	}
                    }
                    
                }
            });
            return cell ;
        });
        
        leftPane = new StackPane();
        leftPane.getChildren().add(placeTree);
        mainPane.setLeft(leftPane);
	}
	
	private void changeCenterContent(Section s){
		centerWebEngine.loadContent(s.getContent());
	}
	
	private void changeCenterContent(String s){
		centerWebEngine.loadContent(s);
	}
}