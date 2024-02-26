package flight.GUI;

import java.awt.EventQueue;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Aircraft;
import entity.Airline;
import entity.Flight;
import entity.Seat;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;

import dataBase.ReadDB;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Payment extends JPanel{
	protected static final Object[][] seatInfo = null;
	private JTable table;
	private JTable table_1;
	private JTextField textField;
	private JTable table_2;
	private JTable table_3;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private String track_seat_num;
	private String flight_ID;


	public void addNewSeat(String user, Aircraft airPlane, String seatNum, String flight_ID, Airline al) throws SQLException {
		Connection dbConnect;
		dbConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/air_travel", "ensf480", "ensf480");


		int craft_ID = airPlane.getID();
		int perRow = airPlane.getSeatMap()[0].length;
		char letter = seatNum.charAt(0);
		int letterNum = (char)letter - 'A';
		int tempCount = perRow * letterNum;
		char digit = seatNum.charAt(1);
		int digitNum = Character.getNumericValue(digit);
		tempCount += (digitNum + 1);
		Character seat_flight = (char)('A'+(Integer.parseInt(flight_ID)/100)-1);
		String seat_ID = Integer.toString(tempCount) + seat_flight;
		System.out.println(seat_ID);

		String update = "UPDATE air_travel.Seats SET Passenger_Name = '" + user + "' WHERE Aircraft_ID = " + craft_ID + " AND SeatNum = '" + seat_ID + "'";
		Statement st = dbConnect.createStatement();
		st.executeUpdate(update);

		Seat[][] seatList = airPlane.getSeatMap();
		for (int i = 0; i < al.getListOfUsers().size(); i++) {
			if (al.getListOfUsers().get(i).getUsername().equals(user)) {
				seatList[letterNum][digitNum].reserveSeat(al.getListOfUsers().get(i));
			}
		}


		return;
	}


	/**
	 * Create the application.
	 *
	 *
	 */
	public String[][] seatDisplay(Aircraft airPlane,String seatNum){

		Seat[][] seatList = airPlane.getSeatMap();
		char letter = seatNum.charAt(0);
		char digit = seatNum.charAt(1);
		int letterNum = (char)letter - 'A';
		int digitNum = Character.getNumericValue(digit);
		//  Seat[][] seatList = airPlane.getSeatMap();
		String[][] seatInfo = new String[1][3];
		//  for (int i = 0; i < seatList.length; i++) {
		// 	for (int j = 0; j < seatList[i].length; j++) {
		seatInfo[0][0] = (seatNum);
		if(seatList[letterNum][digitNum].getSeatType().equals("First")) {
			seatInfo[0][1] = "$2000";
			seatInfo[0][2] = "First Class";
		}
		else if(seatList[letterNum][digitNum].getSeatType().equals("Business")) {
			seatInfo[0][1]="$1200";
			seatInfo[0][2] = "Business Class";
		}
		else if(seatList[letterNum][digitNum].getSeatType().equals("Economy")) {
			seatInfo[0][1]="$400";
			seatInfo[0][2] = "Economy Class";
		}

		//   return seatInfo;



		// 					}

		//  				}
		return seatInfo;
	}

	public Payment(JFrame main, String user,Aircraft airPlane,String ID, Airline al){

		flight_ID = ID;
		Seat[][] seatList = airPlane.getSeatMap();
		String[][] seatAvailability = new String[seatList.length][seatList[0].length];
		String[][] seatDisplay = new String[seatList.length][seatList[0].length];
		String[][] seatType = new String[seatList.length][seatList[0].length];
		for (int i = 0; i < seatList.length; i++) {
			for (int j = 0; j < seatList[i].length; j++) {
				seatDisplay[i][j] = ((char)(i+65) + Integer.toString(j));
				String type = seatList[i][j].getSeatType();
				seatType [i][j] = type;
				boolean reserved = seatList[i][j].reservedSeat();
				if (reserved) {
					String availability = "Booked";
					seatAvailability[i][j] = availability;
				}
				else {
					String availability = "Free";
					seatAvailability[i][j] = availability;
				}
			}
		}
		String[] header = new String[seatList[0].length];
		for (int i = 0; i < header.length; i++) {
			header[i] = "";
		}
		setLayout(null);

		JTextField seat= new JTextField();
		seat.setBounds(89, 79, 130, 26);
		add(seat);
		seat.setColumns(10);
		JTable seatView = new JTable(seatDisplay,header);
		seatView.setBounds(6, 6, 438, 62);
		seatView.setEnabled(false);
		add(seatView);

		JLabel select = new JLabel("Select Seat");
		select.setBounds(16, 84, 90, 16);
		add(select);

		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seatNum = seat.getText();
				track_seat_num = seatNum;
				String[][] seatInfo = seatDisplay(airPlane,seatNum);
				String Titles [] = {"NUMBER","Price","Type"};
				JTable table_2 = new JTable(seatInfo,Titles);
				table_2.setBounds(6, 114, 438, 26);;
				add(table_2);
				main.revalidate();






//				main.setContentPane(new Payment2(main ,user, airPlane, seatInfo));
//				main.revalidate();
//


			}
		});

		btnNewButton.setBounds(231, 80, 117, 29);
		add(btnNewButton);
		textField_1 = new JTextField();
		textField_1.setBounds(89, 155, 202, 26);
		add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(132, 188, 82, 26);
		add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(92, 209, 82, 26);
		add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel = new JLabel("Card Number");
		lblNewLabel.setBounds(16, 160, 61, 16);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Expiry (MM/YY)");
		lblNewLabel_1.setBounds(16, 193, 104, 16);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("CVV");
		lblNewLabel_2.setBounds(16, 214, 61, 16);
		add(lblNewLabel_2);


		JButton btnNewButton_1 = new JButton("Pay");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cardNumber = textField_1.getText();
				String expiryDate = textField_2.getText();
				String cvv = textField_3.getText();
				if(cardNumber.length()==19 && cvv.length()==3 && expiryDate.length()==5) {
					try {
						addNewSeat(user, airPlane, track_seat_num, flight_ID, al);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					main.setContentPane(new Payment2(main ,user, al));
					main.revalidate();
				}
			}
		});
		btnNewButton_1.setBounds(6, 242, 117, 29);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Sign out");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				main.setContentPane(new Login(main, al));
				main.revalidate();
			}
		});
		btnNewButton_2.setBounds(121, 242, 117, 29);
		add(btnNewButton_2);

	}

}

