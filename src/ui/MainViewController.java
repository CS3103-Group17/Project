package ui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.ImageData;
import model.Section;
import storage.DataController;
import util.concurrent.CategoryThreadController;

import util.Constants.Category;
import model.SearchField;

public class MainViewController {

	private BorderPane mainPane;
	private BorderPane topPane;
	private StackPane leftPane;
	private StackPane centerPane;
	
	private TopPaneController topCon;
	private LeftPaneController leftCon;
	private CenterPaneController centerCon;
	
	private DataController dataController = DataController.INSTANCE;
	private CategoryThreadController threadController = CategoryThreadController.INSTANCE;
	
	private final double WINDOW_WIDTH = 1000.0;
	private final double WINDOW_HEIGHT = 600.0;

	// @@author A0139963N
	public MainViewController() {
		topCon = new TopPaneController(this);
		leftCon = new LeftPaneController(dataController.getDisplayCities(), this);
		centerCon = new CenterPaneController();
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
		centerPane = centerCon.getCenterPane();
		
		mainPane.setCenter(centerPane);
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
	    threadController.execute(Category.ALL, new SearchField(keyword));
	    
	    setUpLeftPane();
	}
	
	public void changeCenterContent(Section s){
		centerCon.changeCenterContent(s);
	}
	
	public void changeCenterContent(String s){
		centerCon.changeCenterContent(s);
	}
	
	public void loadTrendHTML(String html){
		centerCon.loadTrendHTML(html);
	}
	
	public void loadGallery(ArrayList<ImageData> images){
		centerCon.loadGallery(images);
	}
}