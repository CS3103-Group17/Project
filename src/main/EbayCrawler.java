package main;

// use java sdk to authenticate
import com.ebay.soap.eBLBaseComponents.*;
import com.ebay.sdk.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.axis.types.URI.MalformedURIException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class EbayCrawler {
	
	public final static String EBAY_APP_ID = "AdamGan-ECommerc-PRD-59f210a48-6b92885d";
    public final static String EBAY_FINDING_SERVICE_URI = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME="
            + "{operation}&SERVICE-VERSION={version}&SECURITY-APPNAME="
            + "{applicationId}&GLOBAL-ID={globalId}&keywords={keywords}"
            + "&paginationInput.entriesPerPage={maxresults}";
    public static final String SERVICE_VERSION = "1.0.0";
    public static final String OPERATION_NAME = "findItemsByKeywords";
    public static final String GLOBAL_ID = "EBAY-SG";
    public final static int REQUEST_DELAY = 3000;
    public final static int MAX_RESULTS = 10;
    private int maxResults;

    public EbayCrawler() {
        this.maxResults = MAX_RESULTS;
    }

    public EbayCrawler(int maxResults) {
        this.maxResults = maxResults;
    }

    public void run(String tag) throws Exception {

        String address = createAddress(tag);
        print("sending request to :: ", address);
        String response = ebayURLReader.read(address);
        print("response :: ", response);
        //process xml dump returned from EBAY
        processResponse(response);
        //Honor rate limits - wait between results
        Thread.sleep(REQUEST_DELAY);


    }

    private String createAddress(String tag) {

        //substitute token
        String address = EbayCrawler.EBAY_FINDING_SERVICE_URI;
        address = address.replace("{version}", EbayCrawler.SERVICE_VERSION);
        address = address.replace("{operation}", EbayCrawler.OPERATION_NAME);
        address = address.replace("{globalId}", EbayCrawler.GLOBAL_ID);
        address = address.replace("{applicationId}", EbayCrawler.EBAY_APP_ID);
        address = address.replace("{keywords}", tag);
        address = address.replace("{maxresults}", "" + this.maxResults);

        return address;

    }

    private void processResponse(String response) throws Exception {


        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();


        Document doc = builder.parse(is);
        //OutputStream myOutput = new FileOutputStream("sample.txt");
        //printDocument(doc, myOutput);
        XPathExpression ackExpression = xpath.compile("//findItemsByKeywordsResponse/ack");
        XPathExpression itemExpression = xpath.compile("//findItemsByKeywordsResponse/searchResult/item");

        String ackToken = (String) ackExpression.evaluate(doc, XPathConstants.STRING);
        print("ACK from ebay API :: ", ackToken);
        if (!ackToken.equals("Success")) {
            throw new Exception(" service returned an error");
        }
        
        XPathExpression resultExpression = xpath.compile("//findItemsByKeywordsResponse/searchResult");
        NodeList tmpCountList = (NodeList) resultExpression.evaluate(doc, XPathConstants.NODESET);
        Node tmpCount = tmpCountList.item(0);
        //Node tmpCount = (Node) resultExpression;
        //Node tmpCount = (Node) resultExpression.evaluate(doc, XPathConstants.STRING);
        String resultCount =  (String) xpath.evaluate("@count", tmpCount, XPathConstants.STRING);
        print("Item count: ", resultCount);
        
        if(Integer.parseInt(resultCount) == 0) {
        	//no results
        }
        else {
        	NodeList nodes = (NodeList) itemExpression.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) {

                Node node = nodes.item(i);

                String itemId = (String) xpath.evaluate("itemId", node, XPathConstants.STRING);
                String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
                String itemUrl = (String) xpath.evaluate("viewItemURL", node, XPathConstants.STRING);
                String galleryUrl = (String) xpath.evaluate("galleryURL", node, XPathConstants.STRING);
                
                String categoryId = (String) xpath.evaluate("primaryCategory/categoryId", node, XPathConstants.STRING);
            	String categoryName = (String) xpath.evaluate("primaryCategory/categoryName", node, XPathConstants.STRING);
                /*
                NodeList tempCategory = (NodeList) xpath.evaluate("primaryCategory", node, XPathConstants.NODESET);
                
                for (int j = 0; j < tempCategory.getLength(); j++) {
                	Node tmpNode = tempCategory.item(j);
                	
                	String categoryId = (String) xpath.evaluate("categoryId", tmpNode, XPathConstants.STRING);
                	String categoryName = (String) xpath.evaluate("categoryName", tmpNode, XPathConstants.STRING);
                }
                */
                String paymentMethod = (String) xpath.evaluate("paymentMethod", node, XPathConstants.STRING);
                boolean autoPay = resolveBoolean((String) xpath.evaluate("autoPay", node, XPathConstants.STRING));
                String postalCode = (String) xpath.evaluate("postalCode", node, XPathConstants.STRING);
                String location = (String) xpath.evaluate("location", node, XPathConstants.STRING);
                String country = (String) xpath.evaluate("country", node, XPathConstants.STRING);
                
                String shippingServiceCost  = (String) xpath.evaluate("shippingInfo/shippingServiceCost", node, XPathConstants.STRING);
                String shippingCurrencyId = (String) xpath.evaluate("shippingInfo/shippingServiceCost/@currencyId", node, XPathConstants.STRING);
                String shippingType = (String) xpath.evaluate("shippingInfo/shippingType", node, XPathConstants.STRING);
                String shipToLocations = (String) xpath.evaluate("shippingInfo/shipToLocations", node, XPathConstants.STRING);
                boolean expeditedShipping = resolveBoolean((String) xpath.evaluate("shippingInfo/expeditedShipping", node, XPathConstants.STRING));
                boolean oneDayShippingAvailable = resolveBoolean((String) xpath.evaluate("shippingInfo/oneDayShippingAvailable", node, XPathConstants.STRING));
                String handlingTime = (String) xpath.evaluate("shippingInfo/handlingTime", node, XPathConstants.STRING);
                
                /*
                NodeList tempShipping = (NodeList) xpath.evaluate("shippingInfo", node, XPathConstants.NODESET);
                
                for (int j = 0; j < tempShipping.getLength(); j++) {
                	Node tmpNode = tempShipping.item(j);
                	
                	String shippingServiceCost  = (String) xpath.evaluate("shippingServiceCost", node, XPathConstants.STRING);
                    String currencyId = (String) xpath.evaluate("currencyId", node, XPathConstants.STRING);
                    String shippingType = (String) xpath.evaluate("shippingType", node, XPathConstants.STRING);
                    String shipToLocations = (String) xpath.evaluate("shipToLocations", node, XPathConstants.STRING);
                    boolean expeditedShipping = resolveBoolean((String) xpath.evaluate("expeditedShipping", node, XPathConstants.STRING));
                    boolean oneDayShippingAvailable = resolveBoolean((String) xpath.evaluate("oneDayShippingAvailable", node, XPathConstants.STRING));
                    String handlingTime = (String) xpath.evaluate("handlingTime", node, XPathConstants.STRING);
                }
                */
                String currentPrice = (String) xpath.evaluate("sellingStatus/currentPrice", node, XPathConstants.STRING);
                String sellingCurrencyId = (String) xpath.evaluate("sellingStatus/currentPrice/@currencyId", node, XPathConstants.STRING);
                
                String convertedCurrentPrice = (String) xpath.evaluate("sellingStatus/convertedCurrentPrice", node, XPathConstants.STRING);
                String sellingConvertedCurrencyId = (String) xpath.evaluate("sellingStatus/convertedCurrentPrice/@currencyId", node, XPathConstants.STRING);
                
                String conditionDisplayName = (String) xpath.evaluate("conditionDisplayName", node, XPathConstants.STRING);

                print("currentPrice", currentPrice);
                print("sellingCurrencyId", sellingCurrencyId);
                print("convertedCurrentPrice", convertedCurrentPrice);
                print("sellingConvertedCurrencyId", sellingConvertedCurrencyId);
                
                print("itemId", itemId);
                print("title", title);
                print("galleryUrl", galleryUrl);
                print("categoryId", categoryId);
                print("categoryName", categoryName);
                print("paymentMethod", paymentMethod);
                print("autoPay", autoPay);
                print("postalCode", postalCode);
                print("location", location);
                print("country", country);
                
                print("shippingServiceCost", shippingServiceCost);
                print("shippingCurrencyId", shippingCurrencyId);
                print("shippingType", shippingType);
                print("shipToLocations", shipToLocations);
                
                print("expeditedShipping", expeditedShipping);
                print("oneDayShippingAvailable", oneDayShippingAvailable);
                print("handlingTime", handlingTime);
                print("conditionDisplayName", conditionDisplayName);
                
                System.out.println();
                System.out.println();

            }
        	
        }

        is.close();

    }
    
    private boolean resolveBoolean(String bool) {
    	if (bool.equalsIgnoreCase("true")) {
    		return true;
    	}
    	return false;
    }
    
    private void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), 
             new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    private void print(String name, String value) {
        System.out.println(name + "::" + value);
    }
    
    private void print(String name, boolean value) {
        System.out.println(name + "::" + value);
    }

  public static void main(String[] args) throws Exception {
  	
	  EbayCrawler driver = new EbayCrawler();	//default 10 results
      String tag = "cd";
      driver.run(java.net.URLEncoder.encode(tag, "UTF-8"));
      
  }
  
  public void findByName(String query, int numberOfMaxResults) throws Exception {
	  	
	  EbayCrawler driver = new EbayCrawler(numberOfMaxResults);
      driver.run(java.net.URLEncoder.encode(query, "UTF-8"));
      
      //return type TODO
      
  }

  /*
//  Read properties file to load developer credentials
    Properties keys = new Properties();
    //String root = System.getProperty("user.dir");
    //System.out.println(root);
    try {
        keys.load(new FileInputStream("ebay/keys.properties"));
    } catch (IOException e) {
        System.out.println(e);
    }
    
    // set credentials in ApiAccount
    ApiAccount account = new ApiAccount();
    account.setDeveloper( keys.getProperty("devId") );
    account.setApplication( keys.getProperty("appId") );
    account.setCertificate( keys.getProperty("certId") );
    
    // set credentials in ApiCredential
    ApiCredential credential = new ApiCredential();
    credential.setApiAccount( account );
    credential.seteBayToken( keys.getProperty("token") );
    
    // add ApiCredential to ApiContext
    ApiContext context = new ApiContext();
    context.setApiCredential(credential);
  
    // set eBay server URL to call
    //context.setApiServerUrl( "https://api.sandbox.ebay.com/wsapi" );  // sandbox
    context.setApiServerUrl( "http://svcs.ebay.com/services/search/FindingService/v1" );  // production
    
    // set timeout in milliseconds - 3 minutes
    context.setTimeout(180000);
    
    // set wsdl version number
    context.setWSDLVersion("423");
    
    // turn on logging to standard out
    ApiLogging logging = new ApiLogging();
    logging.setLogHTTPHeaders(true);
    logging.setLogSOAPMessages(true);
    logging.setLogExceptions(true);
    context.setApiLogging(logging);
    
    
    // create ApiCall object - we'll use it to make the call
    ApiCall call = new ApiCall( context );
    
    //example to get time
    // create soap api request and response objects
    GeteBayOfficialTimeRequestType request = new GeteBayOfficialTimeRequestType();
    AbstractResponseType response;
    
    //com.ebay.soap.eBLBaseComponents.GetContextualKeywordsRequestType;
    */
    /* public final static String EBAY_FINDING_SERVICE_URI = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME="
            + "{operation}&SERVICE-VERSION={version}&SECURITY-APPNAME="
            + "{applicationId}&GLOBAL-ID={globalId}&keywords={keywords}"
            + "&paginationInput.entriesPerPage={maxresults}";
     
    
     */

    
    // make the call and handle the response
    /*
    try {
    	//api calls
    	
    	response = call.executeByApiName("GeteBayOfficialTime", request);
    	
        // Get the ebay time
        // Result inherits from AbstractResponseType
        Calendar cal = response.getTimestamp();            // ebay time is returned in gmt 
		
        // Display official ebay time in gmt
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;   // java months go from 0-11; make human readable
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour24 = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        System.out.println("official ebay time in gmt is: " + year + "-" + month + "-" + day + " " + hour24 + ":" + min + ":" + sec);
        
        // Convert ebay time to local time and display
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
        System.out.println("in your time zone " + formatter.format( cal.getTime() ));

        
    } catch (ApiException ae) {
    	System.out.println(ae);
    } catch (SdkSoapException sse) {
    	System.out.println(sse);
    } catch (SdkException se) {
    	System.out.println(se);
    }
    	
  }
  */
 }

