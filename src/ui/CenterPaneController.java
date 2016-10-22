package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.ImageData;
import model.Section;
import utils.ContentParser;

public class CenterPaneController {

	private StackPane centerPane;
	
	private TilePane galleryPane;
	
	private WebView webView;
	private WebEngine centerWebEngine;
	
	private final int GALLERY_PANE_WIDTH = 800;
	private final int IMG_PANE_WIDTH = 150;
	
	public CenterPaneController(){
		centerPane = new StackPane();
		galleryPane = new TilePane();
		galleryPane.setHgap(8);
		galleryPane.setPrefColumns(4);
		galleryPane.maxWidth(GALLERY_PANE_WIDTH);
		webView = new WebView();
        centerWebEngine = webView.getEngine();
	}
	
	public StackPane getCenterPane(){
		return centerPane;
	}
	
	public void changeCenterContent(Section s){
		centerWebEngine.loadContent(s.getContent());
		centerPane.getChildren().clear();
		centerPane.getChildren().add(webView);
	}
	
	public void changeCenterContent(String s){
		centerWebEngine.loadContent(s);
		centerPane.getChildren().clear();
		centerPane.getChildren().add(webView);
	}
	
	public void loadTrendHTML(String html){
		centerWebEngine.loadContent(html);
		centerPane.getChildren().clear();
		centerPane.getChildren().add(webView);
	}
	
	public void loadGallery(ArrayList<ImageData> images){
		galleryPane.getChildren().clear();
		
		for(ImageData id: images){
			System.out.println(id.getURL());
			BorderPane bp = new BorderPane();
			bp.setMaxWidth(IMG_PANE_WIDTH);
			
			
			try {
				URL oracle = new URL(id.getURL());
				Image img = new Image(oracle.openStream(), IMG_PANE_WIDTH, 0, true, true);
				
				ImageView iv = new ImageView(img);
				iv.setFitWidth(IMG_PANE_WIDTH);
				iv.maxWidth(IMG_PANE_WIDTH);
				Label lbl = new Label(ContentParser.parseImageTitle(id.getName()));
				lbl.setTextAlignment(TextAlignment.CENTER);
				lbl.maxWidth(IMG_PANE_WIDTH);
				lbl.setWrapText(true);
				bp.setCenter(iv);
				bp.setBottom(lbl);
				
				galleryPane.getChildren().add(bp);
			} catch (IOException e) {
				System.out.println("Failed to parse Image");
				e.printStackTrace();
			}
		}
		
		centerPane.getChildren().clear();
		centerPane.getChildren().add(galleryPane);
	}
}
