package ui;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import model.flights.Carrier;
import model.flights.Flight;
import model.flights.Itinerary;
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
	private TreeItem<String> flightsItem;
	private TreeItem<String> socialsItem;
    
	private ArrayList<City> displayCities;
	
	private int hotelCounter;
	private String hotelHtml;

	private int flightCounter;
	private String flightHtml;
	private String socialHtml;
	
	private CategoryThreadController threadController = CategoryThreadController.INSTANCE;

	@FXML
	public void initialize() {
	    displayTilePane = new TilePane();
	    displayTilePane.setHgap(8);
	    displayTilePane.setPrefColumns(4);
	    
	    displayWebView = new WebView();
	    
	    displayCities = new ArrayList<City>();
	    hotelCounter = 0;
	    flightCounter = 0;
	    hotelHtml = "";
	    flightHtml = "";
	    socialHtml = "";
	    
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
        if(hotelCounter % 3 == 0) {
            hotelHtml += "<div id=\"pricing-table\" class=\"clear\">\r\n";
        }
        
        hotelHtml += "\t<div class=\"plan\">\r\n        ";
        hotelHtml += "<h3>" + hotel.getname();
        hotelHtml += "<span>S$" + hotel.gettotalPrice() + "</span>";
        hotelHtml += "</h3>\r\n        ";
        hotelHtml += "<img style=\"margin: 3px 3px;\" src=\"" + hotel.getURL() + "\" width=\"140\" height=\"140\" />       \r\n        ";
        hotelHtml += "<ul>\r\n            ";
        hotelHtml += "<li><b>Address</b></br> " + hotel.getaddress() + "</li>\r\n            ";
        hotelHtml += "<li><b>Description</b></br> " + hotel.getroomDescription() + "</li>\t\t\r\n        ";
        hotelHtml += "</ul> \r\n    ";
        hotelHtml += "</div>\r\n";
        
        if(hotelCounter % 3 == 2) {
            hotelHtml += "</div>\r\n";
        }
        
        hotelCounter = (hotelCounter + 1) % 3;
    }
    
    public void setFlightItem(Itinerary itinerary) {
    	if(flightCounter % 3 == 0) {
            flightHtml += "<div id=\"pricing-table\" class=\"clear\">\r\n";
        }
        
    	Carrier carrier = itinerary.getOutboundLeg().getCarriers().get(0);
    	Flight outgoingFlight = itinerary.getOutboundLeg().getFlights().get(0);
    	Flight inboundFlight = itinerary.getInboundLeg().getFlights().get(0);
    	Double price = Double.parseDouble(itinerary.getPrice());
    	DecimalFormat fmt = new DecimalFormat("0.00");
    	DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("HH:mm");
    	//System.out.println("Logo"+carrier.getImageURL());
    	
    	flightHtml += "\t<div class=\"plan\">\r\n        ";
    	flightHtml += "<h3>" + carrier.getName();
    	flightHtml += "<span>S$" + fmt.format(price) + "</span>";
    	flightHtml += "</h3>\r\n        ";
    	flightHtml += "<img style=\"margin: 3px 3px;\" src=\"" + carrier.getImageURL() + "\" width=\"140\" />       \r\n        ";
    	flightHtml += "<ul>\r\n            ";
    	flightHtml += "<li><b>Flight Number</b></br> " + outgoingFlight.getCarrier().getDisplayCode() + outgoingFlight.getFlightNo() + "</br>" + inboundFlight.getCarrier().getDisplayCode() + inboundFlight.getFlightNo() + "</li>\r\n            ";
    	flightHtml += "<li><b>Duration</b></br> " + itinerary.getOutboundLeg().getDuration() + " minutes<br/>" + itinerary.getInboundLeg().getDuration() + " minutes</li>\t\t\r\n        ";
    	if(itinerary.getOutboundLeg().getDepartureTime() != null && itinerary.getOutboundLeg().getArrivalTime() != null)
    		flightHtml += "<li><b>Time</b></br> " + itinerary.getOutboundLeg().getDepartureTime().format(dtFmt) + " to " + itinerary.getOutboundLeg().getArrivalTime().format(dtFmt);
    	if(itinerary.getInboundLeg().getDepartureTime() != null && itinerary.getInboundLeg().getArrivalTime() != null)
    		flightHtml += "<br/>" + itinerary.getInboundLeg().getDepartureTime().format(dtFmt) + " to " + itinerary.getInboundLeg().getArrivalTime().format(dtFmt) + "</li>\t\t\r\n        ";
    	flightHtml += "</ul> \r\n    ";
    	flightHtml += "</div>\r\n";
        
        if(flightCounter % 3 == 2) {
        	flightHtml += "</div>\r\n";
        }
        
        flightCounter = (flightCounter + 1) % 3;
    }
    
    public void setSocialItem(InstagramData instagram) {
    	socialHtml += "<blockquote class=\"twitter-tweet\">\r\n";
    	socialHtml += "\t<div>\r\n        ";
        socialHtml += "<img style=\"margin: 10px 10px;\" src=\"" + instagram.getImageURL() + "\" width=\"300\"/>       \r\n        ";
        socialHtml += "<ul>\r\n            ";
        socialHtml += "<p>@"+instagram.getUserHandle()+"</br> " + instagram.getCaption() + "</p>\r\n            ";
        socialHtml += "</ul> \r\n    ";
        socialHtml += "</div>\r\n";
        socialHtml += "</blockquote>\r\n";
        
    }
    
    public void setSocialItem(Tweet tweet) {
    	socialHtml += "<blockquote class=\"twitter-tweet\">\r\n";
    	socialHtml += "\t<div>\r\n        ";
        socialHtml += "<img style=\"margin: 10px 10px;\" src=\"" + tweet.getImageURL() + "\" width=\"300\"/>       \r\n        ";
        socialHtml += "<ul>\r\n            ";
        socialHtml += "<p>@"+tweet.getUserHandle()+"</br> " + tweet.getText() + "</p>\r\n            ";
        socialHtml += "</ul> \r\n    ";
        socialHtml += "</div>\r\n";
        socialHtml += "</blockquote>\r\n";
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
            
            hotelCounter = 0;
            hotelHtml = "";

            socialHtml = "";
            
            travelLocationTreeView.getRoot().setExpanded(true);
            
            disableSearch();
            new Thread(new DisplayThread(this)).start();
            
            threadController.execute(Category.ALL, searchField);
        }
	}
	
	private void setTravelLocation() {
        TreeItem<String> rootItem =  new TreeItem<String>("Travel location");

        citiesItem = new TreeItem<String>("Information");
        hotelsItem = new TreeItem<String>("Hotels");
        flightsItem = new TreeItem<String>("Flights");
        socialsItem = new TreeItem<String>("Socials");
        
        rootItem.getChildren().add(citiesItem);
        rootItem.getChildren().add(hotelsItem);
        rootItem.getChildren().add(flightsItem);
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
                    if (treeCellItem.getParent() == null) {
                        return;
                    }
                    
                    
                    TreeItem<String> parent = treeCellItem.getParent();
                    
                    /* If not null, means at 3rd level or below. */
                    if (parent.getParent() != null) {
                        /* Find the child's ancestor. */
                        while (!parent.getParent().getValue().equals("Travel location")) {
                            parent = parent.getParent();
                        }
                    } else {
                        parent = treeCellItem;
                    }

                    switch (parent.getValue()) {
                        default:
                            break;
                    
                        case "Information":
                            setCityItemDisplay(treeCellItem);
                            break;
                            
                        case "Hotels":
                            setHotelItemDisplay();
                            break;
                            
                        case "Flights":
                            setFlightItemDisplay();
                            break;
                            
                        case "Socials":
                            setSocialItemDisplay();
                            break;
                    }
                }
            });
            
            return treeCell;
        });
	}
	
	private void setCityItemDisplay(TreeItem<String> cityItem) {
	    if (cityItem.getValue().equals("Information")) {
	        return;
	    }
	    
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
	
	private void setHotelItemDisplay() {
        String html = "<html>\r\n<head>\r\n\t<style type=\"text/css\">\r\n\tbody{\r\n  background: #fff;\r\n}\r\n\r\n#pricing-table {\r\n\tmargin: 10px auto;\r\n\ttext-align: center;\r\n\twidth: 670px; /* total computed width = 222 x 3 + 226 */\r\n\tborder: 1px solid #ddd;\r\n}\r\n\r\n#pricing-table .plan {\r\n\tfont: 12px 'Lucida Sans', 'trebuchet MS', Arial, Helvetica;\r\n\ttext-shadow: 0 1px rgba(255,255,255,.8);        \r\n\tbackground: #fff;      \r\n\tborder: 1px solid #ddd;\r\n\tcolor: #333;\r\n\tpadding: 20px;\r\n\twidth: 180px; /* plan width = 180 + 20 + 20 + 1 + 1 = 222px */      \r\n\tfloat: left;\r\n\tposition: relative;\r\n}\r\n\r\n#pricing-table #most-popular {\r\n\tz-index: 2;\r\n\ttop: -13px;\r\n\tborder-width: 3px;\r\n\tpadding: 20px 20px;\r\n\t-moz-border-radius: 5px;\r\n\t-webkit-border-radius: 5px;\r\n\tborder-radius: 5px;\r\n\t-moz-box-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);\r\n\t-webkit-box-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);\r\n\tbox-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);    \r\n}\r\n\r\n#pricing-table .plan:nth-child(1) {\r\n\t-moz-border-radius: 5px 0 0 5px;\r\n\t-webkit-border-radius: 5px 0 0 5px;\r\n\tborder-radius: 5px 0 0 5px;        \r\n}\r\n\r\n#pricing-table .plan:nth-child(4) {\r\n\t-moz-border-radius: 0 5px 5px 0;\r\n\t-webkit-border-radius: 0 5px 5px 0;\r\n\tborder-radius: 0 5px 5px 0;        \r\n}\r\n\r\n/* --------------- */\t\r\n\r\n#pricing-table h3 {\r\n\tfont-size: 20px;\r\n\tfont-weight: normal;\r\n\tpadding: 15px;\r\n\tmargin: -20px -20px 50px -20px;\r\n\tbackground-color: #eee;\r\n\tbackground-image: -moz-linear-gradient(#fff,#eee);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#eee));    \r\n\tbackground-image: -webkit-linear-gradient(#fff, #eee);\r\n\tbackground-image: -o-linear-gradient(#fff, #eee);\r\n\tbackground-image: -ms-linear-gradient(#fff, #eee);\r\n\tbackground-image: linear-gradient(#fff, #eee);\r\n}\r\n\r\n#pricing-table #most-popular h3 {\r\n\tbackground-color: #ddd;\r\n\tbackground-image: -moz-linear-gradient(#eee,#ddd);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#eee), to(#ddd));    \r\n\tbackground-image: -webkit-linear-gradient(#eee, #ddd);\r\n\tbackground-image: -o-linear-gradient(#eee, #ddd);\r\n\tbackground-image: -ms-linear-gradient(#eee, #ddd);\r\n\tbackground-image: linear-gradient(#eee, #ddd);\r\n\tmargin-top: -30px;\r\n\tpadding-top: 30px;\r\n\t-moz-border-radius: 5px 5px 0 0;\r\n\t-webkit-border-radius: 5px 5px 0 0;\r\n\tborder-radius: 5px 5px 0 0; \t\t\r\n}\r\n\r\n#pricing-table .plan:nth-child(1) h3 {\r\n\t-moz-border-radius: 5px 0 0 0;\r\n\t-webkit-border-radius: 5px 0 0 0;\r\n\tborder-radius: 5px 0 0 0;       \r\n}\r\n\r\n#pricing-table .plan:nth-child(4) h3 {\r\n\t-moz-border-radius: 0 5px 0 0;\r\n\t-webkit-border-radius: 0 5px 0 0;\r\n\tborder-radius: 0 5px 0 0;       \r\n}\t\r\n\r\n#pricing-table h3 span {\r\n\tdisplay: block;\r\n\tfont: bold 25px/100px Georgia, Serif;\r\n\tcolor: #777;\r\n\tbackground: #fff;\r\n\tborder: 5px solid #fff;\r\n\theight: 120px;\r\n\twidth: 120px;\r\n\tmargin: 5px auto -65px;\r\n\t-moz-border-radius: 100px;\r\n\t-webkit-border-radius: 100px;\r\n\tborder-radius: 100px;\r\n\t-moz-box-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n\t-webkit-box-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n\tbox-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n}\r\n\r\n/* --------------- */\r\n\r\n#pricing-table ul {\r\n\tmargin: 20px 0 0 0;\r\n\tpadding: 0;\r\n\tlist-style: none;\r\n}\r\n\r\n#pricing-table li {\r\n\tborder-top: 1px solid #ddd;\r\n\tpadding: 10px 0;\r\n}\r\n\r\n/* --------------- */\r\n\t\r\n#pricing-table .signup {\r\n\tposition: relative;\r\n\tpadding: 8px 20px;\r\n\tmargin: 20px 0 0 0;  \r\n\tcolor: #fff;\r\n\tfont: bold 14px Arial, Helvetica;\r\n\ttext-transform: uppercase;\r\n\ttext-decoration: none;\r\n\tdisplay: inline-block;       \r\n\tbackground-color: #72ce3f;\r\n\tbackground-image: -moz-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#72ce3f), to(#62bc30));    \r\n\tbackground-image: -webkit-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -o-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -ms-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: linear-gradient(#72ce3f, #62bc30);\r\n\t-moz-border-radius: 3px;\r\n\t-webkit-border-radius: 3px;\r\n\tborder-radius: 3px;     \r\n\ttext-shadow: 0 1px 0 rgba(0,0,0,.3);        \r\n\t-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n\t-webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n\tbox-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n}\r\n\r\n#pricing-table .signup:hover {\r\n\tbackground-color: #62bc30;\r\n\tbackground-image: -moz-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#62bc30), to(#72ce3f));      \r\n\tbackground-image: -webkit-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -o-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -ms-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: linear-gradient(#62bc30, #72ce3f); \r\n}\r\n\r\n#pricing-table .signup:active, #pricing-table .signup:focus {\r\n\tbackground: #62bc30;       \r\n\ttop: 2px;\r\n\t-moz-box-shadow: 0 0 3px rgba(0, 0, 0, .7) inset;\r\n\t-webkit-box-shadow: 0 0 3px rgba(0, 0, 0, .7) inset;\r\n\tbox-shadow: 0 0 3px rgba(0, 0, 0, .7) inset; \r\n}\r\n\r\n/* --------------- */\r\n\r\n.clear:before, .clear:after {\r\n  content:\"\";\r\n  display:table\r\n}\r\n\r\n.clear:after {\r\n  clear:both\r\n}\r\n\r\n.clear {\r\n  zoom:1\r\n}\r\n\t</style>\r\n</head>\r\n<body>\r\n";
        
        html += hotelHtml;
        html += "</body>\r\n</html>";
        
        loadWebView(html);
	}
	
	private void setFlightItemDisplay() {
		String html = "<html>\r\n<head>\r\n\t<style type=\"text/css\">\r\n\tbody{\r\n  background: #fff;\r\n}\r\n\r\n#pricing-table {\r\n\tmargin: 10px auto;\r\n\ttext-align: center;\r\n\twidth: 670px; /* total computed width = 222 x 3 + 226 */\r\n\tborder: 1px solid #ddd;\r\n}\r\n\r\n#pricing-table .plan {\r\n\tfont: 12px 'Lucida Sans', 'trebuchet MS', Arial, Helvetica;\r\n\ttext-shadow: 0 1px rgba(255,255,255,.8);        \r\n\tbackground: #fff;      \r\n\tborder: 1px solid #ddd;\r\n\tcolor: #333;\r\n\tpadding: 20px;\r\n\twidth: 180px; /* plan width = 180 + 20 + 20 + 1 + 1 = 222px */      \r\n\tfloat: left;\r\n\tposition: relative;\r\n}\r\n\r\n#pricing-table #most-popular {\r\n\tz-index: 2;\r\n\ttop: -13px;\r\n\tborder-width: 3px;\r\n\tpadding: 20px 20px;\r\n\t-moz-border-radius: 5px;\r\n\t-webkit-border-radius: 5px;\r\n\tborder-radius: 5px;\r\n\t-moz-box-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);\r\n\t-webkit-box-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);\r\n\tbox-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);    \r\n}\r\n\r\n#pricing-table .plan:nth-child(1) {\r\n\t-moz-border-radius: 5px 0 0 5px;\r\n\t-webkit-border-radius: 5px 0 0 5px;\r\n\tborder-radius: 5px 0 0 5px;        \r\n}\r\n\r\n#pricing-table .plan:nth-child(4) {\r\n\t-moz-border-radius: 0 5px 5px 0;\r\n\t-webkit-border-radius: 0 5px 5px 0;\r\n\tborder-radius: 0 5px 5px 0;        \r\n}\r\n\r\n/* --------------- */\t\r\n\r\n#pricing-table h3 {\r\n\tfont-size: 20px;\r\n\tfont-weight: normal;\r\n\tpadding: 15px;\r\n\tmargin: -20px -20px 50px -20px;\r\n\tbackground-color: #eee;\r\n\tbackground-image: -moz-linear-gradient(#fff,#eee);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#eee));    \r\n\tbackground-image: -webkit-linear-gradient(#fff, #eee);\r\n\tbackground-image: -o-linear-gradient(#fff, #eee);\r\n\tbackground-image: -ms-linear-gradient(#fff, #eee);\r\n\tbackground-image: linear-gradient(#fff, #eee);\r\n}\r\n\r\n#pricing-table #most-popular h3 {\r\n\tbackground-color: #ddd;\r\n\tbackground-image: -moz-linear-gradient(#eee,#ddd);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#eee), to(#ddd));    \r\n\tbackground-image: -webkit-linear-gradient(#eee, #ddd);\r\n\tbackground-image: -o-linear-gradient(#eee, #ddd);\r\n\tbackground-image: -ms-linear-gradient(#eee, #ddd);\r\n\tbackground-image: linear-gradient(#eee, #ddd);\r\n\tmargin-top: -30px;\r\n\tpadding-top: 30px;\r\n\t-moz-border-radius: 5px 5px 0 0;\r\n\t-webkit-border-radius: 5px 5px 0 0;\r\n\tborder-radius: 5px 5px 0 0; \t\t\r\n}\r\n\r\n#pricing-table .plan:nth-child(1) h3 {\r\n\t-moz-border-radius: 5px 0 0 0;\r\n\t-webkit-border-radius: 5px 0 0 0;\r\n\tborder-radius: 5px 0 0 0;       \r\n}\r\n\r\n#pricing-table .plan:nth-child(4) h3 {\r\n\t-moz-border-radius: 0 5px 0 0;\r\n\t-webkit-border-radius: 0 5px 0 0;\r\n\tborder-radius: 0 5px 0 0;       \r\n}\t\r\n\r\n#pricing-table h3 span {\r\n\tdisplay: block;\r\n\tfont: bold 25px/100px Georgia, Serif;\r\n\tcolor: #777;\r\n\tbackground: #fff;\r\n\tborder: 5px solid #fff;\r\n\theight: 120px;\r\n\twidth: 120px;\r\n\tmargin: 5px auto -65px;\r\n\t-moz-border-radius: 100px;\r\n\t-webkit-border-radius: 100px;\r\n\tborder-radius: 100px;\r\n\t-moz-box-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n\t-webkit-box-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n\tbox-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n}\r\n\r\n/* --------------- */\r\n\r\n#pricing-table ul {\r\n\tmargin: 20px 0 0 0;\r\n\tpadding: 0;\r\n\tlist-style: none;\r\n}\r\n\r\n#pricing-table li {\r\n\tborder-top: 1px solid #ddd;\r\n\tpadding: 10px 0;\r\n}\r\n\r\n/* --------------- */\r\n\t\r\n#pricing-table .signup {\r\n\tposition: relative;\r\n\tpadding: 8px 20px;\r\n\tmargin: 20px 0 0 0;  \r\n\tcolor: #fff;\r\n\tfont: bold 14px Arial, Helvetica;\r\n\ttext-transform: uppercase;\r\n\ttext-decoration: none;\r\n\tdisplay: inline-block;       \r\n\tbackground-color: #72ce3f;\r\n\tbackground-image: -moz-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#72ce3f), to(#62bc30));    \r\n\tbackground-image: -webkit-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -o-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -ms-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: linear-gradient(#72ce3f, #62bc30);\r\n\t-moz-border-radius: 3px;\r\n\t-webkit-border-radius: 3px;\r\n\tborder-radius: 3px;     \r\n\ttext-shadow: 0 1px 0 rgba(0,0,0,.3);        \r\n\t-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n\t-webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n\tbox-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n}\r\n\r\n#pricing-table .signup:hover {\r\n\tbackground-color: #62bc30;\r\n\tbackground-image: -moz-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#62bc30), to(#72ce3f));      \r\n\tbackground-image: -webkit-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -o-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -ms-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: linear-gradient(#62bc30, #72ce3f); \r\n}\r\n\r\n#pricing-table .signup:active, #pricing-table .signup:focus {\r\n\tbackground: #62bc30;       \r\n\ttop: 2px;\r\n\t-moz-box-shadow: 0 0 3px rgba(0, 0, 0, .7) inset;\r\n\t-webkit-box-shadow: 0 0 3px rgba(0, 0, 0, .7) inset;\r\n\tbox-shadow: 0 0 3px rgba(0, 0, 0, .7) inset; \r\n}\r\n\r\n/* --------------- */\r\n\r\n.clear:before, .clear:after {\r\n  content:\"\";\r\n  display:table\r\n}\r\n\r\n.clear:after {\r\n  clear:both\r\n}\r\n\r\n.clear {\r\n  zoom:1\r\n}\r\n\t</style>\r\n</head>\r\n<body>\r\n";
        
        html += flightHtml;
        html += "</body>\r\n</html>";
        
        loadWebView(html);
	}
	
	private void setSocialItemDisplay() {
		String html = "<html>\r\n<head>\r\n\t<style type=\"text/css\">";
		html += "blockquote.twitter-tweet { display: inline-block; font-family: \"Helvetica Neue\", Roboto, \"Segoe UI\", Calibri, sans-serif;";
		html += "font-size: 12px; font-weight: bold; line-height: 16px; border-color: #eee #ddd #bbb; border-radius: 5px; border-style: solid; border-width: 1px; box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15); margin: 10px 5px; padding: 0 16px 16px 16px; max-width: 468px; }";
		html += "blockquote.twitter-tweet p { font-size: 16px; font-weight: normal; line-height: 20px; }";
		html += "blockquote.twitter-tweet a { color: inherit; font-weight: normal; text-decoration: none; outline: 0 none; }";
		html += "blockquote.twitter-tweet a:hover, blockquote.twitter-tweet a:focus { text-decoration: underline;}";
	    html += "</style>\r\n</head>\r\n<body>\r\n";
        
        html += socialHtml;
        html += "</body>\r\n</html>";
        
        loadWebView(html);   
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
