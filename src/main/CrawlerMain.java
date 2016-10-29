package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import storage.PersistentStorage;

public class CrawlerMain extends Application {
    private static final String APPLICATION_FXML_FILEPATH = "../ui/CrawlerUI.fxml";
    private static final String APPLICATION_NAME = "Travel Crawler";
    
	public static void main(String[] args) {
	    initComponents();
		    
	    launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    primaryStage.initStyle(StageStyle.DECORATED);
	    primaryStage.setTitle(APPLICATION_NAME);
	    
	    initUserInterface(primaryStage);
	}

    /* Initialize all the prerequisites of the program here. */
    private static void initComponents() {
        PersistentStorage.INSTANCE.loadAllStorage();
    }   	
	
	private void initUserInterface(Stage primaryStage) {
	    FXMLLoader loader = new FXMLLoader();
	    
	    loader.setLocation(getClass().getResource(APPLICATION_FXML_FILEPATH));
	    setScene(primaryStage, loader);
	    
	    primaryStage.show();
	}
    
    private void setScene(Stage primaryStage, FXMLLoader loader) {
        try {
            /* This point will throw IOException if resource is not found in the directory or other weird issue occurs. */
            Scene scene = new Scene((BorderPane) loader.load());
            scene.setFill(Color.TRANSPARENT);

            primaryStage.setScene(scene);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
