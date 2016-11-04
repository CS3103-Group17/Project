package model.flights;

public class Flight {

	private String flightNo;
	private Carrier carrier;
	
	public Flight(String flightNo, Carrier carrier){
		setFlightNo(flightNo);
		setCarrier(carrier);
	}
	
	public String getFlightNo() {
		return flightNo;
	}
	
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	public Carrier getCarrier() {
		return carrier;
	}
	
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
}
