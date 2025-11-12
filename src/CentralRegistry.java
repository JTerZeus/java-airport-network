import java.util.ArrayList;
public class CentralRegistry{
	private static ArrayList<Flight> flights = new ArrayList<Flight>();
	private static ArrayList<Airport> airports = new ArrayList<Airport>();
	

	public static void addFlight(Flight aFlight) {
		flights.add(aFlight);
		if(!aFlight.getA1().isDirectlyConnectedTo(aFlight.getA2())) {
			aFlight.getA1().addAirport(aFlight.getA2());
			aFlight.getA2().addAirport(aFlight.getA1());	
		} 
		if(!aFlight.getA1().airlines.contains(aFlight.getAirline())) {
			aFlight.getA1().airlines.add(aFlight.getAirline());
		}
		if(!aFlight.getA2().airlines.contains(aFlight.getAirline())) {
			aFlight.getA2().airlines.add(aFlight.getAirline());
		}
		
		
	}
	public static void addAirport(Airport anAirport) {
		airports.add(anAirport);
		
	}
	public static Airport getLargestHub() {
		int max=0;
		int index=-1;
		for(Airport A: airports) {
			if(A.getConnectedAirports().size()>max) {
				max=A.getConnectedAirports().size();
				index=airports.indexOf(A);
				
			}
		}
		return airports.get(index);
	}
	
	public static Flight getLongestFlight() {
		int max=0;
		int index=-1;
		for(Flight F: flights) {
			if(F.getFlightDuration()>max) {
				max=F.getFlightDuration();
				index=flights.indexOf(F);
			}
		}
		return flights.get(index);
				
		
	}
	public static Airport getAirport(String cityName) {
		int index=-1;
		for(Airport A: airports) {
			if(A.getCity().equals(cityName)) {
				index=airports.indexOf(A);
				return airports.get(index);
				
			}
		}
		return null;
		
	}
	public static String getDirectFlightsDetails(Airport a, Airport b) {
		String s="";
		int i=1;
		for(Flight f: flights) {
			if((f.getA1().equals(a) && f.getA2().equals(b)) ||
					(f.getA1().equals(b) && f.getA2().equals(a))) {
				s+="["+i+"]"+f.toString()+"\n";
				i++;
			}
				
			
		}
		return s;
	}
	public static String getInDirectFlightsDetails(Airport a, Airport b) {
		String s="";
		int i=1;
		for(Airport A: airports) {
			if(A.isDirectlyConnectedTo(a)&& A.isDirectlyConnectedTo(b)) {
			s+="["+i+"]"+A.getCity()+","+A.getCodeName()+" airport"+"\n";
			i++;
			}
		}
		return s;
	}
	
	
	

}
