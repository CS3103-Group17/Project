package ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
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
import storage.Storage;
import wikitravel.WikitravelController;

public class MainViewController {

	private BorderPane mainPane;
	private BorderPane topPane;
	private StackPane leftPane;
	private ScrollPane centerPane;
	
	private TopPaneController topCon;
	private LeftPaneController leftCon;
	
	private WikitravelController wtCon;
	private WebEngine centerWebEngine;
	
	private final double WINDOW_WIDTH = 1000.0;
	private final double WINDOW_HEIGHT = 600.0;

	// @@author A0139963N
	public MainViewController(Storage s) {
		wtCon = new WikitravelController(s);
		//wtCon.parseCategory();
		
		topCon = new TopPaneController(this);
		leftCon = new LeftPaneController(s.getDisplayCities(), this);
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
		topPane = topCon.getTopPane();
		topPane.setPadding(new Insets(14.0));
		
		mainPane.setTop(topPane);
	}
	
	private void setUpLeftPane(){   
		leftCon.populateTree();
		leftPane = leftCon.getLeftPane();
        mainPane.setLeft(leftPane);
	}
	
	public void search(String keyword){
		wtCon.searchPage(keyword);
		setUpLeftPane();
	}
	
	public void changeCenterContent(Section s){
		centerWebEngine.loadContent(s.getContent());
	}
	
	public void changeCenterContent(String s){
		centerWebEngine.loadContent(s);
	}
}