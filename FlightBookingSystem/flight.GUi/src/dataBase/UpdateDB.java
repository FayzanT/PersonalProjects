package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import entity.Airline;
import entity.Seat;

public class UpdateDB {

	private Airline al;
	private ResultSet results;
	private Connection dbConnect;

	public UpdateDB() {
		al = Airline.getAirline();
		createConnection();
		try {
			update();
		} catch (SQLException e) {
			System.out.println("Problem updating db");
			e.printStackTrace();
		}
		closeConnection();

	}

	public void createConnection() {
		try {
			dbConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/air_travel", "ensf480", "ensf480");
		} catch (SQLException e) {
			System.out.println("Problem establishing connection");
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			dbConnect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() throws SQLException {
		updateUsers();
		updateFlights();
		updateAircrafts();
		updateSeats();
	}

	public void updateUsers() throws SQLException {
		String constraint = "SET FOREIGN_KEY_CHECKS = 0";
		Statement st = dbConnect.createStatement();
		st.execute(constraint);
		String update = "TRUNCATE TABLE Login";
		st = dbConnect.createStatement();
		st.executeUpdate(update);

		for (int i = 0; i < al.getListOfUsers().size(); i++) {
			String query = "insert into air_travel.login (Username, Password, Email, Type) VALUES (?,?,?,?)";
			PreparedStatement ps = dbConnect.prepareStatement(query);
			ps.setString(1, al.getListOfUsers().get(i).getUsername());
			ps.setString(2, al.getListOfUsers().get(i).getPassword());
			ps.setString(3, al.getListOfUsers().get(i).getEmail());
			ps.setString(4, al.getListOfUsers().get(i).getPriviledge());
			ps.execute();

		}

	}

	public void updateFlights() throws SQLException {
		String update = "TRUNCATE TABLE Flight";
		Statement st = dbConnect.createStatement();
		st.executeUpdate(update);

		for (int i = 0; i < al.getListOfFlights().size(); i++) {

			String query = "insert into air_travel.flight (ID, Destination, Time, Aircraft_ID) VALUES (?,?,?, ?)";
			PreparedStatement ps = dbConnect.prepareStatement(query);
			ps.setInt(1, al.getListOfFlights().get(i).getID());
			ps.setString(2, al.getListOfFlights().get(i).getDestination());
			ps.setObject(3, al.getListOfFlights().get(i).getFlightDate());
			ps.setInt(4, al.getListOfFlights().get(i).getPlane().getID());
			ps.execute();

		}


	}

	public void updateAircrafts() throws SQLException {
		String update = "TRUNCATE TABLE Aircrafts";
		Statement st = dbConnect.createStatement();
		st.executeUpdate(update);

		for (int i = 0; i < al.getListOfAircrafts().size(); i++) {
			String query = "insert into air_travel.Aircrafts (ID, size) VALUES (?,?)";
			PreparedStatement ps = dbConnect.prepareStatement(query);
			ps.setInt(1, al.getListOfAircrafts().get(i).getID());
			ps.setString(2, al.getListOfAircrafts().get(i).getSize());
			ps.execute();

		}


	}

	public void updateSeats() throws SQLException {
		int craftCount = al.getListOfAircrafts().size();
		int k =1;
		for (int i = 0; i < craftCount; i++) {
			Seat[][] currSeatMap = al.getListOfAircrafts().get(i).getSeatMap();
			if (al.getListOfAircrafts().get(i).used() == true) {



				//update
				for (Seat[] seatRow : currSeatMap) {
					for (Seat curSeat : seatRow) {
						if (curSeat.reservedSeat() && curSeat.reservedFor() != null) {
							String seatPassengerName = curSeat.reservedFor().getUsername();
							Character seat_flight = (char)('A'+(al.getListOfFlights().get(i).getID()/100)-1);
							String seat_id = Integer.toString(k) + seat_flight;


							// Update the database
							String update = "UPDATE air_travel.Seats SET Passenger_Name = '" + seatPassengerName + "' WHERE Aircraft_ID = " + al.getListOfAircrafts().get(i).getID() + " AND SeatNum = '" + seat_id + "'";
							Statement st = dbConnect.createStatement();
							st.executeUpdate(update);




						}
						k++;
					}
				}




			}
		}
	}
}