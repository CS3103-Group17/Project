/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.morgner.expedia.api;

import com.google.gson.annotations.Expose;

/**
 *
 * @author Christian Morgner
 */
public class ExpediaHotelRoom {

	@Expose
	private String policy = null;
	
	@Expose
	private String rateCode = null;
	
	@Expose
	private String roomTypeCode = null;
	
	@Expose
	private String rateDescription = null;
	
	@Expose
	private String roomTypeDescription = null;
	
	@Expose
	private String supplierType = null;
	
	@Expose
	private String otherInformation = null;
	
	@Expose
	private boolean immediateChargeRequired = false;
	
	@Expose
	private String propertyId = null;
	
	@Expose
	private String smokingPreferences = null;
	
	@Expose
	private int minGuestAge = 0;
	
	@Expose
	private int quotedOccupancy = 0;
	
	@Expose
	private int rateOccupancyPerRoom = 0;
	
	@Expose
	private String deepLink = null;
	
	@Expose
	private ExpediaBedTypeList BedTypes = null;
	
	@Expose
	private ExpediaValueAddList ValueAdds = null;
	
	@Expose
	private ExpediaRateInfoList RateInfos = null;

	public String getPolicy() {
		return policy;
	}

	public String getRateCode() {
		return rateCode;
	}

	public String getRoomTypeCode() {
		return roomTypeCode;
	}

	public String getRateDescription() {
		return rateDescription;
	}

	public String getRoomTypeDescription() {
		return roomTypeDescription;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public String getOtherInformation() {
		return otherInformation;
	}

	public boolean isImmediateChargeRequired() {
		return immediateChargeRequired;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public String getSmokingPreferences() {
		return smokingPreferences;
	}

	public int getMinGuestAge() {
		return minGuestAge;
	}

	public int getQuotedOccupancy() {
		return quotedOccupancy;
	}

	public int getRateOccupancyPerRoom() {
		return rateOccupancyPerRoom;
	}

	public String getDeepLink() {
		return deepLink;
	}

	public ExpediaBedTypeList getBedTypes() {
		return BedTypes;
	}

	public ExpediaValueAddList getValueAdds() {
		return ValueAdds;
	}

	public ExpediaRateInfoList getRateInfos() {
		return RateInfos;
	}
}

/*
{
  "HotelRoomAvailabilityResponse":{
    "@size":"12",
    "customerSessionId":"0ABAAA4F-765C-2B91-43C2-F7D05D396B5B",
    "hotelId":326875,
    "arrivalDate":"02\/09\/2014",
    "departureDate":"02\/11\/2014",
    "hotelName":"Hotel Unique Novum",
    "hotelAddress":"Hoher Wall 38",
    "hotelCity":"Dortmund",
    "hotelCountry":"DE",
    "numberOfRoomsRequested":1,
    "checkInInstructions":"<p><b>Nationale Bewertung<\/b> <br \/>Die Hotelstars Union vergibt offiziell Sternebeurteilungen für Hotels in diesem Land: Deutschland. Dieses Haus wurde mit 4 Sternen ausgezeichnet.<\/p><p><b>Gebühren<\/b> <br \/><p>Das Hotel erhebt beim Check-in\/Check-out, bzw. wenn die entsprechende Leistung in Anspruch genommen wird, folgende Gebühren und Kautionen: <\/p> <ul> <li>Aufpreis für das Frühstück: ca. 9,50 EUR pro Person<\/li><li>Parken ohne Parkservice: 13 EUR pro Nacht<\/li> <li>Für frühen Check-in wird eine Gebühr erhoben. <\/li> <li>Für späten Check-out wird eine Gebühr erhoben. <\/li>  <\/ul> <p>Die oben aufgeführte Liste enthält eventuell nicht alle Informationen. Gebühren und Kautionen enthalten eventuell keine Steuern und können sich ändern. <\/p><\/p>",
    "tripAdvisorRating":4,
    "tripAdvisorReviewCount":140,
    "tripAdvisorRatingUrl":"http:\/\/www.tripadvisor.com\/img\/cdsi\/img2\/ratings\/traveler\/4.0-12345-4.gif",
    "HotelRoomResponse":[
    {
      "rateCode":200584709,
      "roomTypeCode":200105447,
      "rateDescription":"Comfort-Doppelzimmer zur Einzelbelegung",
      "roomTypeDescription":"Comfort-Doppelzimmer zur Einzelbelegung",
      "supplierType":"E",
      "propertyId":2935892,
      "smokingPreferences":"NS",
      "rateOccupancyPerRoom":1,
      "quotedOccupancy":1,
      "minGuestAge":0,
      "RateInfos":
      {
	"@size":"1",
	"RateInfo":
	{
	  "@priceBreakdown":"true",
	  "@promo":"true",
	  "@rateChange":"true",
	  "RoomGroup":
	  {
	    "Room":
	    {
	      "numberOfAdults":1,
	      "numberOfChildren":0,
	      "rateKey":"82363468-5f13-4a2e-b040-b9f359860dbc"
	    }
	  },
	  "ChargeableRateInfo":
	  {
	    "@averageBaseRate":"50.5",
	    "@averageRate":"45.45",
	    "@commissionableUsdTotal":"123.12",
	    "@currencyCode":"EUR",
	    "@maxNightlyRate":"52.2",
	    "@nightlyRateTotal":"90.9",
	    "@total":"90.9",
	    "NightlyRatesPerRoom":
	    {
	      "@size":"2",
	      "NightlyRate":
	      [
		{
		  "@baseRate":"43.0",
		  "@rate":"38.7",
		  "@promo":"true"
		},
		{
		  "@baseRate":"58.0",
		  "@rate":"52.2",
		  "@promo":"true"
		}
	      ]
	    }
	  },
	  "cancellationPolicy":"Dieses Angebot ist nicht erstattungsfähig und kann nicht geändert oder storniert werden. Wenn Sie diese Buchung ändern oder stornieren, wird Ihnen Ihr Geld nicht zurückerstattet.",
	  "CancelPolicyInfoList":
	  {
	    "CancelPolicyInfo":
	    [
	      {
		"versionId":208899893,
		"cancelTime":"19:00:00",
		"startWindowHours":0,
		"nightCount":0,
		"percent":100,
		"currencyCode":"EUR",
		"timeZoneDescription":"(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna"
	      }
	    ]
	  },
	  "nonRefundable":true,
	  "rateType":"MerchantStandard",
	  "promoId":202846951,
	  "promoDescription":"10% Rabatt",
	  "promoType":"Standard",
	  "currentAllotment":8,
	  "guaranteeRequired":false,
	  "depositRequired":true,
	  "taxRate":"0.00"
	}
      },
      "ValueAdds":
      {
	"@size":"1",
	"ValueAdd":
	{
	  "@id":"2048",
	  "description":"Kostenloses W-Lan"
	}
      },
      "deepLink":"https:\/\/travel.ian.com\/templates\/55505\/hotels\/326875\/book?lang=de&amp;currency=EUR&amp;standardCheckin=02\/09\/2014&amp;standardCheckout=02\/11\/2014&amp;selectedPrice=90.900000&amp;supplierType=E&amp;rateCode=200584709&amp;roomTypeCode=200105447&amp;roomsCount=1&amp;rooms[0].adultsCount=1&amp;rateKey=82363468-5f13-4a2e-b040-b9f359860dbc"
    }
    ]

 */
