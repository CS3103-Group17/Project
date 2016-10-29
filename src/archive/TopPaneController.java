package archive;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class TopPaneController {

	private MainViewController mvc;
	
	private BorderPane pane;
	private TextField searchBox;
	private Button searchBtn;
	
	public TopPaneController(MainViewController mvc){
		this.mvc = mvc;
		pane = new BorderPane();
		
		searchBox = new TextField();
		searchBox.setPromptText("Enter Destination: e.g. San Francisco");
		searchBox.setOnKeyPressed(event -> {
			  if (event.getCode() == KeyCode.ENTER){
				  handleSearch();
			  }
		});
		
		searchBtn = new Button("Search");
		searchBtn.setOnAction(event -> handleSearch());
		
		pane.setCenter(searchBox);
		pane.setRight(searchBtn);
	}
	
	private void handleSearch(){
		mvc.search(searchBox.getText());
	}
	
	public BorderPane getTopPane(){
		return pane;
	}
}
