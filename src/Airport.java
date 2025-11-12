import java.util.ArrayList;
public class Airport {
	private String airportName;
	private String codeName;
	private String city;
	private String country;
	private ArrayList<Airport> connectedAirports;
	public ArrayList<String> airlines;
	
	
	
	public Airport(String aName,String aCode,String aCity,String aCountry){
		this.connectedAirports = new ArrayList<Airport>();
		this.airportName=aName;
		this.codeName=aCode;
		this.city=aCity;
		this.country=aCountry;
		this.airlines= new ArrayList<String>();
		
	}
	boolean isDirectlyConnectedTo(Airport anAirport) {
		return this.connectedAirports.contains(anAirport);		
	}
	
	boolean isInDirectlyConnectedTo(Airport anAirport) {
		if(this.isDirectlyConnectedTo(anAirport))
			return false;
		for(Airport A: this.connectedAirports) {
			if(A.isDirectlyConnectedTo(anAirport)) {
				return true;
			}
		}
		return false;
		
	}
	public ArrayList<Airport> getCommonConnections(Airport anAirport){
		ArrayList<Airport> CommonConnections = new ArrayList<Airport>();
		for(Airport A: this.connectedAirports) {
			if(A.isDirectlyConnectedTo(anAirport)) {
				CommonConnections.add(A);
				
			}
		}
		return CommonConnections;
	}
	
	public void printCompanies() {
		for(String airline: this.airlines) {
			System.out.println(airline);
		}
	}
	
	
	
	
	
	
	
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void addAirport(Airport A) {
		this.connectedAirports.add(A);
	}
	public ArrayList<Airport> getConnectedAirports() {
		return this.connectedAirports;
	}
}
