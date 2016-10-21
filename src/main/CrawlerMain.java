package main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import storage.Storage;
import ui.MainViewController;


public class CrawlerMain extends Application {

	private MainViewController mainViewController;
	private final String APPLICATION_NAME = "Travel Crawler";

	private Storage s;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		s = new Storage();
		
		mainViewController = new MainViewController(s);
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
