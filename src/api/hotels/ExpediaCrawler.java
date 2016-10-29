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
import java.time.LocalDate;
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

import model.SearchField;
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
	int numberOfResults = 12;
	ArrayList<Hotel> dataSet = new ArrayList<Hotel>();
	
	public ExpediaCrawler() {}
	
	public ArrayList<Hotel> getHotels(SearchField searchField) {		
		
		String ipAddress = getPublicIP();
		String sig = generateSig();
		String otherElementsStr = "&cid="+customerId+"&minorRev="+minorRev+"&customerUserAgent="+userAgent+"&customerIpAddress="+ipAddress+"&locale="+locale+"&currencyCode="+currency;

		HttpClient httpClient = HttpClientBuilder.create().build();
		
		JSONObject myResult;
		String customerSessionId = "";
		
		LocalDate arrivalDate = searchField.getArrivalDate();
		LocalDate departureDate = searchField.getDepartureDate();
		if (arrivalDate == null) {
		    if (departureDate != null) {
		        arrivalDate = departureDate.minusDays(1);
		    } else {
		        arrivalDate = LocalDate.now();
		    }
		}
		
		if (departureDate == null) {
		    departureDate = arrivalDate.plusDays(1);
		}

		String xml = "<HotelListRequest>";
		
		xml += "<arrivalDate>" + searchField.getDateFormatter().format(arrivalDate) + "</arrivalDate>";
		xml += "<departureDate>" + searchField.getDateFormatter().format(departureDate) + "</departureDate>";
		
		xml += "<RoomGroup><Room>";
		xml += "<numberOfAdults>" + 1 + "</numberOfAdults>";
		xml += "</Room></RoomGroup>";
		
		xml += "<city>" + searchField.getName() + "</city>";
		xml += "<countryCode></countryCode>";
		xml += "<numberOfResults>" + numberOfResults + "</numberOfResults>";
		
		xml += "</HotelListRequest>";
		
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
	        
	        if(response.getStatusLine().getStatusCode() != 200) {
	        	return dataSet;
	        }
	        
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
	
	public String generateHTML() {
			
		String htmlString = "<html>\r\n<head>\r\n\t<style type=\"text/css\">\r\n\tbody{\r\n  background: #fff;\r\n}\r\n\r\n#pricing-table {\r\n\tmargin: 10px auto;\r\n\ttext-align: center;\r\n\twidth: 670px; /* total computed width = 222 x 3 + 226 */\r\n\tborder: 1px solid #ddd;\r\n}\r\n\r\n#pricing-table .plan {\r\n\tfont: 12px 'Lucida Sans', 'trebuchet MS', Arial, Helvetica;\r\n\ttext-shadow: 0 1px rgba(255,255,255,.8);        \r\n\tbackground: #fff;      \r\n\tborder: 1px solid #ddd;\r\n\tcolor: #333;\r\n\tpadding: 20px;\r\n\twidth: 180px; /* plan width = 180 + 20 + 20 + 1 + 1 = 222px */      \r\n\tfloat: left;\r\n\tposition: relative;\r\n}\r\n\r\n#pricing-table #most-popular {\r\n\tz-index: 2;\r\n\ttop: -13px;\r\n\tborder-width: 3px;\r\n\tpadding: 20px 20px;\r\n\t-moz-border-radius: 5px;\r\n\t-webkit-border-radius: 5px;\r\n\tborder-radius: 5px;\r\n\t-moz-box-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);\r\n\t-webkit-box-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);\r\n\tbox-shadow: 20px 0 10px -10px rgba(0, 0, 0, .15), -20px 0 10px -10px rgba(0, 0, 0, .15);    \r\n}\r\n\r\n#pricing-table .plan:nth-child(1) {\r\n\t-moz-border-radius: 5px 0 0 5px;\r\n\t-webkit-border-radius: 5px 0 0 5px;\r\n\tborder-radius: 5px 0 0 5px;        \r\n}\r\n\r\n#pricing-table .plan:nth-child(4) {\r\n\t-moz-border-radius: 0 5px 5px 0;\r\n\t-webkit-border-radius: 0 5px 5px 0;\r\n\tborder-radius: 0 5px 5px 0;        \r\n}\r\n\r\n/* --------------- */\t\r\n\r\n#pricing-table h3 {\r\n\tfont-size: 20px;\r\n\tfont-weight: normal;\r\n\tpadding: 15px;\r\n\tmargin: -20px -20px 50px -20px;\r\n\tbackground-color: #eee;\r\n\tbackground-image: -moz-linear-gradient(#fff,#eee);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#eee));    \r\n\tbackground-image: -webkit-linear-gradient(#fff, #eee);\r\n\tbackground-image: -o-linear-gradient(#fff, #eee);\r\n\tbackground-image: -ms-linear-gradient(#fff, #eee);\r\n\tbackground-image: linear-gradient(#fff, #eee);\r\n}\r\n\r\n#pricing-table #most-popular h3 {\r\n\tbackground-color: #ddd;\r\n\tbackground-image: -moz-linear-gradient(#eee,#ddd);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#eee), to(#ddd));    \r\n\tbackground-image: -webkit-linear-gradient(#eee, #ddd);\r\n\tbackground-image: -o-linear-gradient(#eee, #ddd);\r\n\tbackground-image: -ms-linear-gradient(#eee, #ddd);\r\n\tbackground-image: linear-gradient(#eee, #ddd);\r\n\tmargin-top: -30px;\r\n\tpadding-top: 30px;\r\n\t-moz-border-radius: 5px 5px 0 0;\r\n\t-webkit-border-radius: 5px 5px 0 0;\r\n\tborder-radius: 5px 5px 0 0; \t\t\r\n}\r\n\r\n#pricing-table .plan:nth-child(1) h3 {\r\n\t-moz-border-radius: 5px 0 0 0;\r\n\t-webkit-border-radius: 5px 0 0 0;\r\n\tborder-radius: 5px 0 0 0;       \r\n}\r\n\r\n#pricing-table .plan:nth-child(4) h3 {\r\n\t-moz-border-radius: 0 5px 0 0;\r\n\t-webkit-border-radius: 0 5px 0 0;\r\n\tborder-radius: 0 5px 0 0;       \r\n}\t\r\n\r\n#pricing-table h3 span {\r\n\tdisplay: block;\r\n\tfont: bold 25px/100px Georgia, Serif;\r\n\tcolor: #777;\r\n\tbackground: #fff;\r\n\tborder: 5px solid #fff;\r\n\theight: 120px;\r\n\twidth: 120px;\r\n\tmargin: 5px auto -65px;\r\n\t-moz-border-radius: 100px;\r\n\t-webkit-border-radius: 100px;\r\n\tborder-radius: 100px;\r\n\t-moz-box-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n\t-webkit-box-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n\tbox-shadow: 0 5px 20px #ddd inset, 0 3px 0 #999 inset;\r\n}\r\n\r\n/* --------------- */\r\n\r\n#pricing-table ul {\r\n\tmargin: 20px 0 0 0;\r\n\tpadding: 0;\r\n\tlist-style: none;\r\n}\r\n\r\n#pricing-table li {\r\n\tborder-top: 1px solid #ddd;\r\n\tpadding: 10px 0;\r\n}\r\n\r\n/* --------------- */\r\n\t\r\n#pricing-table .signup {\r\n\tposition: relative;\r\n\tpadding: 8px 20px;\r\n\tmargin: 20px 0 0 0;  \r\n\tcolor: #fff;\r\n\tfont: bold 14px Arial, Helvetica;\r\n\ttext-transform: uppercase;\r\n\ttext-decoration: none;\r\n\tdisplay: inline-block;       \r\n\tbackground-color: #72ce3f;\r\n\tbackground-image: -moz-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#72ce3f), to(#62bc30));    \r\n\tbackground-image: -webkit-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -o-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: -ms-linear-gradient(#72ce3f, #62bc30);\r\n\tbackground-image: linear-gradient(#72ce3f, #62bc30);\r\n\t-moz-border-radius: 3px;\r\n\t-webkit-border-radius: 3px;\r\n\tborder-radius: 3px;     \r\n\ttext-shadow: 0 1px 0 rgba(0,0,0,.3);        \r\n\t-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n\t-webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n\tbox-shadow: 0 1px 0 rgba(255, 255, 255, .5), 0 2px 0 rgba(0, 0, 0, .7);\r\n}\r\n\r\n#pricing-table .signup:hover {\r\n\tbackground-color: #62bc30;\r\n\tbackground-image: -moz-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -webkit-gradient(linear, left top, left bottom, from(#62bc30), to(#72ce3f));      \r\n\tbackground-image: -webkit-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -o-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: -ms-linear-gradient(#62bc30, #72ce3f);\r\n\tbackground-image: linear-gradient(#62bc30, #72ce3f); \r\n}\r\n\r\n#pricing-table .signup:active, #pricing-table .signup:focus {\r\n\tbackground: #62bc30;       \r\n\ttop: 2px;\r\n\t-moz-box-shadow: 0 0 3px rgba(0, 0, 0, .7) inset;\r\n\t-webkit-box-shadow: 0 0 3px rgba(0, 0, 0, .7) inset;\r\n\tbox-shadow: 0 0 3px rgba(0, 0, 0, .7) inset; \r\n}\r\n\r\n/* --------------- */\r\n\r\n.clear:before, .clear:after {\r\n  content:\"\";\r\n  display:table\r\n}\r\n\r\n.clear:after {\r\n  clear:both\r\n}\r\n\r\n.clear {\r\n  zoom:1\r\n}\r\n\t</style>\r\n</head>\r\n<body>\r\n";
		
		int counter = 0;
		for( Hotel tmp : dataSet) {
			if(counter % 3 == 0) {
				htmlString += "<div id=\"pricing-table\" class=\"clear\">\r\n";
			}
			htmlString += "\t<div class=\"plan\">\r\n        <h3>"
					+tmp.getname()+"<span>S$"
					+tmp.gettotalPrice()+"</span></h3>\r\n        <img style=\"margin: 3px 3px;\" src=\""
					+tmp.getURL()+"\" width=\"140\" height=\"140\" />       \r\n        <ul>\r\n            <li><b>Address</b></br> "
					+tmp.getaddress()+"</li>\r\n            <li><b>Description</b></br> "
					+tmp.getroomDescription()+"</li>\t\t\r\n        </ul> \r\n    </div>\r\n";
			
			if(counter % 3 == 2) {
				htmlString += "</div>\r\n";
			}
			counter++;
		}
		
		
		htmlString += "</body>\r\n</html>";
		
        return htmlString;
	}
	
	public ArrayList<Hotel> getHotelsTest(String destination) {
		
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
	        
	        if(response.getStatusLine().getStatusCode() != 200) {
	        	return dataSet;
	        }
	        
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
