package api.hotels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import model.hotels.Hotel;


public class ExpediaCrawler {
	
	String service = "http://api.ean.com/ean-services/rs/hotel/";
	String version = "v3/";
	String method = "list";
	String hotelId = "201252";
	long customerId = 461872;
	int minorRev = 30;
	String userAgent = "Mozilla/5.0%20(Macintosh;U;Intel%20Mac%20OS%20X%2010.4;en-US;rv:1.9.2.2)";
	String currency = "SGD";
	String locale = "en-US";
	String apikey = "3ogl1d5scbaggl6g4gpu4eehh4";
	String secret = "2nbr7o4j17vl8";
	int numberOfResults = 10;

	public ArrayList<Hotel> getHotels(String destination) {
		
		ArrayList<Hotel> dataSet = new ArrayList<Hotel>();
		String ipAddress = getPublicIP();
		String sig = generateSig();
		String otherElementsStr = "&cid="+customerId+"&minorRev="+minorRev+"&customerUserAgent="+userAgent+"&customerIpAddress="+ipAddress+"&locale="+locale+"&currencyCode="+currency;

		HttpClient httpClient = HttpClientBuilder.create().build();
		
		JSONObject myResult;
		String customerSessionId = "";
		
		//variables
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.DATE, 25);
		String arrivalDate = format.format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String departureDate = format.format(cal.getTime());
		
		String xml = "<HotelListRequest><arrivalDate>"+arrivalDate+"</arrivalDate><departureDate>"+departureDate+"</departureDate><RoomGroup>";
		xml += "<Room><numberOfAdults>1</numberOfAdults></Room>";
		xml += "</RoomGroup><city>"+destination+"</city><countryCode></countryCode><numberOfResults>"+numberOfResults+"</numberOfResults></HotelListRequest>";
		xml = translateXmlToUrl(xml);
				
		try {
			String url = service + version + method+ "?apikey=" + apikey
					 + "&sig=" + sig + otherElementsStr + "&customerSessionId=" + customerSessionId+ "&xml=" + xml;
			System.out.println("URL is: "+url);
			HttpGet request = new HttpGet(url);
	        
	        
	        String result = "";
	        //request.addHeader("content-type", "application/json");
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
	        
	        JSONArray tempArray = myResult.getJSONObject("HotelListResponse").getJSONObject("HotelList").getJSONArray("HotelSummary");
	        for (int i = 0; i < tempArray.length(); i++) {
	        	JSONObject temp = tempArray.getJSONObject(i);
	        	dataSet.add(extractDetails(temp));	
	        }
	      
	    } catch (ClientProtocolException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }catch (Exception ex) {
	    	ex.printStackTrace();
	    }
		
        return dataSet;
	}
	
	private Hotel extractDetails(JSONObject map) {
		
		String name = map.getString("name");
		String address1 = map.getString("address1");
		float totalRate = Float.valueOf(map.getJSONObject("RoomRateDetailsList").getJSONObject("RoomRateDetails").getJSONObject("RateInfos").getJSONObject("RateInfo").getJSONObject("ChargeableRateInfo").getString("@total"));
		//float highRate = (float) map.getDouble("highRate");
		//float lowRate = (float) map.getDouble("lowRate");
		String rateCurrencyCode = map.getString("rateCurrencyCode");
		
		String roomDescription = map.getJSONObject("RoomRateDetailsList").getJSONObject("RoomRateDetails").getString("roomDescription");
		//HotelData mydata = new HotelData(name, address1, highRate, lowRate, rateCurrencyCode, roomDescription);
		String url = "http://exp.cdn-hotels.com" + map.getString("thumbNailUrl"); //This is only a 70x70 pix image
		Hotel mydata = new Hotel(name, address1, totalRate, rateCurrencyCode, roomDescription, url);
		//System.out.println(mydata);
		return mydata;
	}
	
	private String translateXmlToUrl(String plain) {
		//%3C = <
		//%3F = >
		//%2F = /
		plain = plain.replaceAll("<", "%3C");
		plain = plain.replaceAll(">", "%3E");
		plain = plain.replaceAll("/", "%2F");
		return plain;
	}
	
	private String getPublicIP() {
		URL whatismyip;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));

			return(in.readLine());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return "";
	}
	
	private	String generateSig(){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			TimeZone tz = TimeZone.getTimeZone("GMT");
			Calendar c = Calendar.getInstance(tz);
			Date now = c.getTime();
			
			long timeInSeconds = (now.getTime() / 1000);
			String input = apikey + secret + timeInSeconds;
			md.update(input.getBytes());
			return (String.format("%032x", new BigInteger(1, md.digest())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private	String convertStreamToString(InputStream is) {

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
