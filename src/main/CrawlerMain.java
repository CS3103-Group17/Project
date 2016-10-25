package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import storage.PersistentStorage;
import ui.MainViewController;

public class CrawlerMain extends Application {
    private static final String APPLICATION_NAME = "Travel Crawler";
    
	private static MainViewController mainViewController;
	
	public static void main(String[] args) {
	    initComponents();
		    
	    launch(args);
	}

	/* Initialize all the prerequisites of the program here. */
	public static void initComponents() {
	    PersistentStorage.INSTANCE.loadAllStorage();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    mainViewController = new MainViewController();
	    
	    primaryStage.setTitle(APPLICATION_NAME);
		
		showMainView(primaryStage);
	}
	
	/**
	 * Shows the Main View for the UI
	 */
	public void showMainView(Stage primaryStage) {
		BorderPane mainView = mainViewController.initialize(primaryStage);

		Scene scene = new Scene(mainView);
		//scene.getStylesheets().add("css/ui.css");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();

		primaryStage.show();
	}
 
}
