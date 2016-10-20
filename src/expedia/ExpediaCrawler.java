package expedia;

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

import de.morgner.expedia.ExpediaClient;
import de.morgner.expedia.rest.ExpediaException;
import de.morgner.expedia.rest.RestClientException;

public class ExpediaCrawler {

	public static void main(String[] args) {
		final long customerId = 461872;
		//final String apiKey = "3ogl1d5scbaggl6g4gpu4eehh4";	//2 L
		//final String apiKey = "3ogI1d5scbaggI6g4gpu4eehh4";		//2 I
		//final String apiKey = "3ogI1d5scbaggl6g4gpu4eehh4";		//I L
		final String apiKey = "3ogl1d5scbaggI6g4gpu4eehh4";		//L I
		final String sharedSecret = "2nbr7o4j17vl8";
		final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
		final String ipAddress = "42.60.195.128";
		final String currency = "SGD";
		final String city = "Seoul";
		final String country = "KR";
		final String locale = "en-SG";
		final String checkIn = "2016-10-28";
		final String checkOut = "2016-11-02";
		final String pax = "2";
		
		/*
		ExpediaClient myExpedia = new ExpediaClient(customerId, apiKey, userAgent, ipAddress);
		
		try {
			myExpedia.getHotelsByCountryAndCity("KR", "Seoul");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (ExpediaException e) {
			e.printStackTrace();
		}
		}
		*/

		HttpClient httpClient = HttpClientBuilder.create().build();
		//HttpClient client = new DefaultHttpClient();
		
		JSONObject myResult;
		
		try {
			//http://api.ean.com/ean-services/rs/hotel/v3/list?cid=461872&apiKey=3ogl1d5scbaggl6g4gpu4eehh4&minorRev=26&currencyCode=EUR&locale=de_DE&customerUserAgent=Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2&customerIpAddress=42.60.195.128&city=Seoul&countryCode=KR&sort=CITY_VALUE
			//String url = "http://api.ean.com/ean-services/rs/hotel/v3/list?cid="+customerId+"&apiKey="+apiKey+"&currencyCode="+currency+"&locale="+locale+"&customerUserAgent="+userAgent+"&customerIpAddress="+ipAddress+"&city="+city+"&countryCode="+country;
			String url = "http://terminal2.expedia.com/x/mhotels/search?city="+city+"&checkInDate="+checkIn+"&checkOutDate="+checkOut+"&room1="+pax+"&apikey="+apiKey+"&cid="+customerId;
			System.out.println("URL is: "+url);
			HttpGet request = new HttpGet(url);
	        
	        
	        String result = "";
	        request.addHeader("content-type", "application/json");
	        HttpResponse response = httpClient.execute(request);
	        HttpEntity entity = response.getEntity();

	        if (entity != null) {

	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            System.out.println("RESPONSE: " + result);
	            instream.close();
	        }
	        
	        org.apache.http.Header[] headers = response.getAllHeaders();
	        for (int i = 0; i < headers.length; i++) {
	            System.out.println(headers[i]);
	        }
	        
	        myResult = new JSONObject(result);
	        
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
