
public class Flight{
	private int flightDuration;
	private String airline;
	private Airport a1;
	private Airport a2;
	public Flight(Airport A1,Airport A2,int duration,String anAirline) {
		this.a1 = A1;
		this.a2 = A2;
		this.flightDuration=duration;
		this.airline=anAirline;
	}
	public int getFlightDuration() {
		return flightDuration;
	}
	public void setFlightDuration(int flightDuration) {
		this.flightDuration = flightDuration;
	}
	public String toString() {
		return ("Flight operated by " + this.airline + ", duration " + this.flightDuration + 
				" minutes");
		
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}

}
