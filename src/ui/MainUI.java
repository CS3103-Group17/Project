package ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainUI {

	private Stage primaryStage;
	private MainViewController mainViewController;

	private final String APPLICATION_NAME = "Travel crawler";

	public MainUI(){
		mainViewController = new MainViewController();
	}
	
	/**
	 * Sets the Stage of the Application
	 */
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(APPLICATION_NAME);
		showMainView();
	}
	
	/**
	 * Shows the Main View for the UI
	 */
	public void showMainView() {
		BorderPane mainView = mainViewController.initialize(primaryStage);

		Scene scene = new Scene(mainView);
		//scene.getStylesheets().add("css/ui.css");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();

		primaryStage.show();
	}

}
