package main;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.MainUI;


public class CrawlerMain extends Application {

	private MainUI ui;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ui = new MainUI();
		ui.setStage(primaryStage);
	}
 
}
