package model.flights;

public class Itinerary {
	private String outboundLegID;
	private String inboundLegID;
	private String price;
	private Leg outboundLeg;
	private Leg inboundLeg;
	
	public Itinerary(String outboundLegID, String inboundLegID, String price){
		setOutboundLegID(outboundLegID);
		setInboundLegID(inboundLegID);
		setPrice(price);
	}
	
	public String getOutboundLegID() {
		return outboundLegID;
	}
	
	public void setOutboundLegID(String outboundLegID) {
		this.outboundLegID = outboundLegID;
	}

	public String getInboundLegID() {
		return inboundLegID;
	}

	public void setInboundLegID(String inboundLegID) {
		this.inboundLegID = inboundLegID;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Leg getOutboundLeg() {
		return outboundLeg;
	}

	public void setOutboundLeg(Leg outboundLeg) {
		this.outboundLeg = outboundLeg;
	}

	public Leg getInboundLeg() {
		return inboundLeg;
	}

	public void setInboundLeg(Leg inboundLeg) {
		this.inboundLeg = inboundLeg;
	}
}
