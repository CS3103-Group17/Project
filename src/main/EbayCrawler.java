package main;

// use java sdk to authenticate
import com.ebay.soap.eBLBaseComponents.*;
import com.ebay.sdk.*;


import org.apache.axis.types.URI.MalformedURIException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;

public class EbayCrawler {

  public static void main(String[] args) throws MalformedURIException, RemoteException {
  	
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
    /*
    //example to get time
    // create soap api request and response objects
    GeteBayOfficialTimeRequestType request = new GeteBayOfficialTimeRequestType();
    AbstractResponseType response;
    
    // make the call and handle the response
    
    try {
    	//api calls
    	
    	response = call.executeByApiName("GeteBayOfficialTime", request);
    	
        // Get the ebay time
        // Result inherits from AbstractResponseType
        Calendar cal = response.getTimestamp();            // ebay time is returned in gmt 
		

        
    } catch (ApiException ae) {
    	System.out.println(ae);
    } catch (SdkSoapException sse) {
    	System.out.println(sse);
    } catch (SdkException se) {
    	System.out.println(se);
    }
    */		
  }
 }

