package archive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class SkyScannerOldCrawler {

	public final static String apiKey = "ad613298088404736599107030638844";
	public final static String market = "SG";
	public final static String currency = "SGD";
	public final static String locale = "en-US";
	public final static String entityID = "27544008"; //ID of location for auto suggest
	public final static String checkIn = "2016-10-28";
	public final static String checkOut = "2016-11-02";
	public final static String pax = "2";
	public final static String rooms = "1";
	
	public static void main(String[] args) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		//HttpClient client = new DefaultHttpClient();
		
		JSONObject myResult;
		
		try {
			//http://partners.api.skyscanner.net/apiservices/hotels/liveprices/v2/{market}/{currency}/{locale}/{entityid}/{checkindate}/{checkoutdate}/{guests}/{rooms}?apiKey={apiKey}[&pageSize={pageSize}][&imageLimit={imageLimit}]
			String url = "http://partners.api.skyscanner.net/apiservices/hotels/liveprices/v2/" + market +"/"+currency+"/"+"/"+locale+"/"+entityID+"/"+checkIn+"/"+checkOut+"/"+pax+"/"+rooms+"?apiKey="+apiKey;
			System.out.println("URL is: "+url);
			HttpGet request = new HttpGet(url);
	        /*
	        GET <URL> HTTP/1.1
			Host: api.skyscanner.net
			Accept: application/json
			
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			String responseBody = httpclient.execute(httppost, responseHandler);
        	JSONObject response=new JSONObject(responseBody);
	         */
	        
	        String result = "";
	        request.addHeader("content-type", "application/json");
	        HttpResponse response = httpClient.execute(request);
	        HttpEntity entity = response.getEntity();

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            System.out.println("RESPONSE: " + result);
	            instream.close();
	        }
	        System.out.println("done response");
	        
	        // Headers
	        org.apache.http.Header[] headers = response.getAllHeaders();
	        for (int i = 0; i < headers.length; i++) {
	            System.out.println(headers[i]);
	        }
	        System.out.println("done headers");
	        
	        myResult = new JSONObject(result);
	        System.out.println("this is the json object:");
	        System.out.println(myResult.toString());
	        
	    } catch (ClientProtocolException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }catch (Exception ex) {
	        // handle exception here
	    }
		

	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

}
