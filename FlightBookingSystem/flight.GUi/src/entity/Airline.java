package entity;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Iterator;

public class Airline {
	private ArrayList<User> listOfUsers;
	private ArrayList<AirlineAgent> listOfAgents;
	private ArrayList<SystemAdmin> listOfAdmins;
	private ArrayList<Flight> listOfFlights;
	private ArrayList<Aircraft> listOfAircrafts;
	
	private static Airline airline;
	
	private Airline() {
		listOfUsers = new ArrayList<User>();
		listOfAgents = new ArrayList<AirlineAgent>();
		listOfAdmins = new ArrayList <SystemAdmin>();
		listOfFlights = new ArrayList<Flight>();
		listOfAircrafts = new ArrayList<Aircraft>();
	}
	
	static public Airline getAirline() {
		if (airline == null) {
			airline = new Airline ();
		}
		return airline;
	}
	
	public User verifyUser(String Username, String Password) {
		for (int i = 0; i < listOfUsers.size(); i++) {
			if(listOfUsers.get(i).getUsername().compareTo(Username) == 0 && listOfUsers.get(i).getPassword().compareTo(Password) == 0) {
				return listOfUsers.get(i);
			}
		}
		return null;
	}
	
	public User verifyAgent(String Username, String Password) {
		for (int i = 0; i < listOfAgents.size(); i++) {
			if(listOfAgents.get(i).getUsername().equals(Username) && listOfAgents.get(i).getPassword().equals(Password)) {
				return listOfAgents.get(i);
			}
		}
		return null;
	}
	
	public User verifyAdmin(String Username, String Password) {
		for (int i = 0; i < listOfAdmins.size(); i++) {
			if(listOfAdmins.get(i).getUsername().equals(Username) && listOfAdmins.get(i).getPassword().equals(Password)) {
				return listOfAdmins.get(i);
			}
		}
		return null;
	}
	
	
	
	public ArrayList<User> getListOfUsers(){
		return listOfUsers;
	}
	
	public ArrayList<AirlineAgent> getListOfAgents(){
		return listOfAgents;
	}
	
	public ArrayList<SystemAdmin> getListOfAdmins(){
		return listOfAdmins;
	}

	public ArrayList<Flight> getListOfFlights(){
		return listOfFlights;
	}


	public ArrayList <Aircraft> getListOfAircrafts(){
		return listOfAircrafts;
	}
	
	public void addUser(String username, String password, String email, String priviledge) {
		listOfUsers.add(new User(username, password, email, priviledge));
	}

	public void addAgent(String username, String password, String email, String priviledge) {
		AirlineAgent agent = new AirlineAgent(username, password, email, priviledge);
		listOfUsers.add(agent);
		listOfAgents.add(agent);
	}

	public void addAdmin(String username, String password, String email, String priviledge){
		SystemAdmin admin = new SystemAdmin(username, password, email, priviledge);
		listOfUsers.add(admin);
		listOfAgents.add(admin);
		listOfAdmins.add(admin);
	}
	
	public boolean addFlight(int id, String destination, LocalDateTime local, Aircraft plane) {
		Flight newFlight = Flight.flightMaker(id, destination, local, plane);
		if (newFlight == null) {
			return false;
		}
		listOfFlights.add(newFlight);
		return true;
	}
	
	public void addAircraft(String size, int id) {
		if (size.equals("Large")) {
			listOfAircrafts.add(new LargeAircraft(id));
		}
		else if (size.equals("Medium")) {
			listOfAircrafts.add(new MediumAircraft(id));
		}
		else if (size.equals("Small")) {
			listOfAircrafts.add(new SmallAircraft(id));
		}
	}
	
	
	
	public void cancelFlight(Flight cancelledFlight) {
		Seat[][] seatMap = cancelledFlight.getPlane().getSeatMap();
		for (int i = 0; i < seatMap.length; i++) {
			for (int j = 0; j < seatMap[i].length; j++) {
				if (seatMap[i][j] != null) {
					seatMap[i][j].reservedFor().sendEmail("Flight to " + cancelledFlight.getDestination() + " on " 
				+ cancelledFlight.getFlightDate() + "has been Cancelled");
				}
			}
		}
		cancelledFlight.getPlane().changeUsed();
	}
	
	public ArrayList<Flight> findFlights(String destination){
	    ArrayList<Flight> matchingFlights = new ArrayList<Flight>();
	
		for (int i = 0; i < listOfFlights.size(); i++) {
			if (listOfFlights.get(i).getDestination().equals(destination) ) {
				matchingFlights.add(listOfFlights.get(i));
			}
		}
		return matchingFlights;
		
	}
}


