package ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import model.ImageData;
import model.SearchField;
import model.Section;
import model.hotels.Hotel;
import model.socials.InstagramData;
import model.socials.Tweet;
import model.travels.City;
import util.ContentParser;
import util.Constants.Category;
import util.concurrent.CategoryThreadController;
import util.concurrent.DisplayThread;

public class CrawlerUIController {
	@FXML
	private Pane searchPane;
    @FXML
    private StackPane travelLocationStackPane;
    @FXML
    private StackPane displayInformationStackPane;	
	
    @FXML
    private Button searchButton;    
    @FXML
    private DatePicker arrivalDatePicker;
    @FXML
    private DatePicker departureDatePicker;    
	@FXML
	private Label searchLabel;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField pointOfInterestTextField;
	@FXML
	private TreeView<String> travelLocationTreeView;
	
	/* Dynamic views. */
	private TilePane displayTilePane;
	private WebView displayWebView;
	
	private TreeItem<String> citiesItem;
	private TreeItem<String> hotelsItem;
	private TreeItem<String> socialsItem;
    
	private ArrayList<City> displayCities;
	
	private CategoryThreadController threadController = CategoryThreadController.INSTANCE;

	@FXML
	public void initialize() {
	    displayTilePane = new TilePane();
	    displayTilePane.setHgap(8);
	    displayTilePane.setPrefColumns(4);
	    
	    displayWebView = new WebView();
	    
	    displayCities = new ArrayList<City>();
	    
	    setTravelLocation();
        setArrivalDate();
        setDepartureDate();
	}

	@FXML
	public void searchPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
		    search();
		}
	}

	@FXML
	public void searchButtonOnClicked(MouseEvent event) {
	    search();
	}
	
	public void enableSearch() {
        searchButton.setDisable(false);
        arrivalDatePicker.setDisable(false);
        departureDatePicker.setDisable(false);
        nameTextField.setDisable(false);
        pointOfInterestTextField.setDisable(false);	    
	}
	
	public void disableSearch() {
	    searchButton.setDisable(true);
	    arrivalDatePicker.setDisable(true);
	    departureDatePicker.setDisable(true);
	    nameTextField.setDisable(true);
	    pointOfInterestTextField.setDisable(true);
	}
	
    public void setCityItem(City city) {
        citiesItem.setExpanded(true);
        
        TreeItem<String> cityItem = new TreeItem<String>(city.getName());
        
        cityItem.getChildren().add(new TreeItem<String>("Trend"));
        cityItem.getChildren().add(new TreeItem<String>("Gallery"));
        
        for (Section section : city.getSections()) {
            TreeItem<String> sectionItem = new TreeItem<String>(section.getName());
            cityItem.getChildren().add(sectionItem);
        }
        
        citiesItem.getChildren().add(cityItem);
        displayCities.add(city);
    }

    public void setHotelItem(Hotel hotel) {

    }
    
    public void setSocialItem(InstagramData instagram) {
        
    }
    
    public void setSocialItem(Tweet tweet) {
        
    }	
	
	private void search() {
        if (!nameTextField.getText().trim().isEmpty()) {
            SearchField searchField = new SearchField(nameTextField.getText());
            
            if (arrivalDatePicker.getValue() != null) {
                searchField.setArrivalDate(arrivalDatePicker.getValue());
            }
            
            if (departureDatePicker.getValue() != null) {
                searchField.setDepartureDate(departureDatePicker.getValue());
            }
            
            if (!pointOfInterestTextField.getText().trim().isEmpty()) {
                searchField.setPointOfInterest(pointOfInterestTextField.getText());
            }
            
            disableSearch();
            new Thread(new DisplayThread(this)).start();
            
            threadController.execute(Category.ALL, searchField);
        }
	}
	
	private void setTravelLocation() {
        TreeItem<String> rootItem =  new TreeItem<String>("Travel location");

        citiesItem = new TreeItem<String>("Information");
        hotelsItem = new TreeItem<String>("Hotels");
        socialsItem = new TreeItem<String>("Socials");
        
        rootItem.getChildren().add(citiesItem);
        rootItem.getChildren().add(hotelsItem);
        rootItem.getChildren().add(socialsItem);

        travelLocationTreeView.setRoot(rootItem);
        
        travelLocationTreeView.setCellFactory(tree -> {
            TreeCell<String> treeCell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean isEmpty) {
                    super.updateItem(item, isEmpty);
                    
                    setText((isEmpty) ? null : item);
                }
            };
            
            treeCell.setOnMouseClicked(event -> {
                if (!treeCell.isEmpty()) {
                    TreeItem<String> treeCellItem = treeCell.getTreeItem();
                    
                    /* If root or root's children, do nothing. */
                    if (treeCellItem.getParent() == null || treeCellItem.getParent().getParent() == null) {
                        return;
                    }
                    
                    /* Find the child's ancestor. */
                    TreeItem<String> parent = treeCellItem.getParent();
                    while (!parent.getParent().getValue().equals("Travel location")) {
                        parent = parent.getParent();
                    }

                    switch (parent.getValue()) {
                        default:
                            break;
                    
                        case "Information":
                            setCityItemDisplay(treeCellItem);
                            break;
                            
                        case "Hotels":
                            setHotelItemDisplay(treeCellItem);
                            break;
                            
                        case "Socials":
                            setSocialItemDisplay(treeCellItem);
                            break;
                    }
                }
            });
            
            return treeCell;
        });
	}
	
	private void setCityItemDisplay(TreeItem<String> cityItem) {
	    String css = "<link rel=\"stylesheet\" href=\"http://wikitravel.org/mw/skins/common/commonContent.css\" />";
	    
	    City city = null;
	    String cityName = (cityItem.isLeaf()) ? cityItem.getParent().getValue() : cityItem.getValue();
	    
	    for (City displayCity : displayCities) {
	        if (displayCity.getName().equals(cityName)) {
	            city = displayCity;
	            break;
	        }
	    }
	    
	    if (city == null) {
	        return;
	    }
	    
	    if (cityItem.isLeaf()) {
	        String value = cityItem.getValue();
	        
	        switch (value) {
	            default:
	                loadWebView(css + city.findSectionByName(value).getContent());
	                break;
	                
	            case "Trend":
	                loadWebView(city.getGoogleTrendsHTML());
	                break;
	                
	            case "Gallery":
	                loadImageGallery(city.getImages());
	                break;
	        }
	        
	    } else {
            String html = city.getSummaryContent() + "<br/>";
            
            for (ImageData image : city.getImages()) {
                html += "<img src=\"" + image.getURL() + "\"/><br/>";
            }
            
            loadWebView(css + html);
	    }
	}
	
	private void setHotelItemDisplay(TreeItem<String> hotelItem) {
	    
	}
	
	private void setSocialItemDisplay(TreeItem<String> socialItem) {
	    
	}
	
	private void loadWebView(String html) {
        displayWebView.getEngine().loadContent(html);
        
        displayInformationStackPane.getChildren().clear();
        displayInformationStackPane.getChildren().add(displayWebView);	    
	}
	
	private void loadImageGallery(ArrayList<ImageData> images) {
	    final int IMAGE_PANE_WIDTH = 150;
	    
        displayTilePane.getChildren().clear();

        for (ImageData imageData : images) {
            System.out.println(imageData.getURL());
            BorderPane borderPane = new BorderPane();
            borderPane.setMaxWidth(IMAGE_PANE_WIDTH);
            
            try {
                Image image = new Image(new URL(imageData.getURL()).openStream(), IMAGE_PANE_WIDTH, 0, true, true);
                ImageView imageView = new ImageView(image);
                
                imageView.setFitWidth(IMAGE_PANE_WIDTH);
                imageView.maxWidth(IMAGE_PANE_WIDTH);
                
                Label label = new Label(ContentParser.parseImageTitle(imageData.getName()));
                label.maxWidth(IMAGE_PANE_WIDTH);
                label.setWrapText(true);
                
                borderPane.setCenter(imageView);
                borderPane.setBottom(label);
                
                displayTilePane.getChildren().add(borderPane);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        displayInformationStackPane.getChildren().clear();
        displayInformationStackPane.getChildren().add(displayTilePane);
	}
	
	private void setArrivalDate() {
	    final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
	        @Override
	        public DateCell call(final DatePicker datePicker) {
	            return new DateCell() {
	                @Override
	                public void updateItem(LocalDate date, boolean isEmpty) {
	                    super.updateItem(date, isEmpty);
	                    
	                    if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusDays(26))) {
	                        setDisable(true);
	                        setStyle("-fx-background-color: #808080; -fx-text-fill: #808080;");
	                    }
	                }
	            };
	        }
	    };
	    
	    arrivalDatePicker.setDayCellFactory(dayCellFactory);
	}
	
    private void setDepartureDate() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean isEmpty) {
                        super.updateItem(date, isEmpty);
                        
                        LocalDate arrivalDate = LocalDate.now();
                        if (arrivalDatePicker.getValue() != null) {
                            arrivalDate = arrivalDatePicker.getValue().plusDays(1);
                        }
                        
                        if (date.isBefore(arrivalDate) || date.isAfter(LocalDate.now().plusDays(27))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #808080; -fx-text-fill: #808080;");
                        }
                    }
                };
            }
        };
        
        departureDatePicker.setDayCellFactory(dayCellFactory);
    }
}
