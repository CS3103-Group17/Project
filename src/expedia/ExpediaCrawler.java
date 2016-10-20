package expedia;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ExpediaCrawler {
	
	static String service = "http://api.ean.com/ean-services/rs/hotel/";
	static String version = "v3/";
	static String method = "list";
	static String hotelId = "201252";
	static long customerId = 461872;
	static int minorRev = 30;
	static String userAgent = "Mozilla/5.0%20(Macintosh;U;Intel%20Mac%20OS%20X%2010.4;en-US;rv:1.9.2.2)";
	static String currency = "SGD";
	static String locale = "en-US";
	static String apikey = "3ogl1d5scbaggl6g4gpu4eehh4";
	static String secret = "2nbr7o4j17vl8";

	public static void main(String[] args) {
		
		String ipAddress = getPublicIP();
		String sig = generateSig();
		String otherElementsStr = "&cid="+customerId+"&minorRev="+minorRev+"&customerUserAgent="+userAgent+"&customerIpAddress="+ipAddress+"&locale="+locale+"&currencyCode="+currency;

		HttpClient httpClient = HttpClientBuilder.create().build();
		
		JSONObject myResult;
		String customerSessionId = "";
		
		/*
		<HotelListRequest>
		<arrivalDate>11/10/2016</arrivalDate>
		<departureDate>11/12/2016</departureDate>
		<RoomGroup>
		<Room>
		<numberOfAdults>2</numberOfAdults>
		<numberOfChildren>1</numberOfChildren>
		<childAges>
		</childAges>
		</Room>
		<Room>
		<numberOfAdults>1</numberOfAdults>
		<numberOfChildren>2</numberOfChildren>
		<childAges>,</childAges>
		</Room>
		</RoomGroup>
		<city>south korea</city>
		<countryCode>
		</countryCode>
		<numberOfResults>20</numberOfResults>
		</HotelListRequest>
		 */
		//variables
		String arrivalDate = "11/10/2016";
		String departureDate = "11/12/2016";
		int rooms = 2;
		int[][] pax = new int[rooms][2];	//[room] [0 adult, 1 child]
		pax[0][0] = 2;
		pax[1][1] = 2;
		String destination = "Seoul"; //can be city or country
		int numberOfResults = 20;
		
		String xml = "<HotelListRequest><arrivalDate>"+arrivalDate+"</arrivalDate><departureDate>"+departureDate+"</departureDate><RoomGroup>";
		for(int i = 0; i< rooms; i++) {
			xml += "<Room><numberOfAdults>"+pax[i][0]+"</numberOfAdults><numberOfChildren>"+pax[i][1]+"</numberOfChildren></Room>";
		}
		xml += "</RoomGroup><city>"+destination+"</city><countryCode></countryCode><numberOfResults>"+numberOfResults+"</numberOfResults></HotelListRequest>";
		xml = translateXmlToUrl(xml);
		
		try {
			//http://api.ean.com/ean-services/rs/hotel/v3/list?cid=461872&apiKey=3ogl1d5scbaggl6g4gpu4eehh4&minorRev=26&currencyCode=EUR&locale=de_DE&customerUserAgent=Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2&customerIpAddress=42.60.195.128&city=Seoul&countryCode=KR&sort=CITY_VALUE
			//String url = "http://api.ean.com/ean-services/rs/hotel/v3/list?cid="+customerId+"&apiKey="+apiKey+"&currencyCode="+currency+"&locale="+locale+"&customerUserAgent="+userAgent+"&customerIpAddress="+ipAddress+"&city="+city+"&countryCode="+country+"&sig="+sig;
			//String url = "http://terminal2.expedia.com/x/mhotels/search?city="+city+"&checkInDate="+checkIn+"&checkOutDate="+checkOut+"&room1="+pax+"&apikey="+apiKey+"&cid="+customerId+"&sig="+sig;
			//String url = service + version + method+ "?apikey=" + apikey
			//		 + "&sig=" + sig + otherElementsStr + "&hotelIdList=" + hotelId;
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
	        
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();

	        String json = gson.toJson(myResult);
	        try (FileWriter file = new FileWriter("Expedia.json")) {
				file.write(json);
			}
	        /*
	        Storage expediaStorage = new Storage();
	        File expediaStore = new File("expedia.json");
	        expediaStorage.setSaveFile("expedia.json");
	        expediaStorage.writeJSONToFile(myResult.toString(), expediaStore);

			try (FileWriter file = new FileWriter("Expedia.txt")) {
				file.write(myResult.toString());
			}
			*/
			
	    } catch (ClientProtocolException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }catch (Exception ex) {
	        // handle exception here
	    	ex.printStackTrace();
	    }
		

	}
	
	private static String translateXmlToUrl(String plain) {
		//%3C = <
		//%3F = >
		//%2F = /
		
		plain = plain.replaceAll("<", "%3C");
		plain = plain.replaceAll(">", "%3E");
		plain = plain.replaceAll("/", "%2F");
		return plain;
	}
	
	private static String getPublicIP() {
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
	
	private static String generateSig(){
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
