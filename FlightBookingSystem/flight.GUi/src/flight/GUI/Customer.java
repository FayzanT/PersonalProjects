package flight.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import entity.Airline;
import dataBase.UpdateDB;
import dataBase.ReadDB;

public class Customer extends JPanel{

	private JFrame main;
	private String user;
	/**
	 * Create the application.
	 */
	public Customer(JFrame main, String user, Airline al) {
		this.main = main;
		this.user = user;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{54, 188, 0, 171, 26, 0};
		gridBagLayout.rowHeights = new int[]{85, 52, 0, 27, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);





		JLabel lblNewLabel = new JLabel("<html><div style='text-align: center;'>Hello " + user + "<br/> Have you heard about our promotions?</div></html>", SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnNewButton = new JButton("View Current Reservations");
		if (user == "guest") {
			btnNewButton.setEnabled(false);
		}
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				main.setContentPane(new ViewExisting(main,user,al));
				main.revalidate();
			}	});
		
		
		JButton btnNewButton_1 = new JButton("Book New Reservation");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 3;
		gbc_btnNewButton_1.gridy = 1;
		add(btnNewButton_1, gbc_btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				main.setContentPane(new BookNew(main,user,al));
				main.revalidate();
			}
		});


		JButton btnPromotion = new JButton("View Promotion");
		GridBagConstraints gbc_btnPromotion = new GridBagConstraints();
		gbc_btnPromotion.insets = new Insets(0, 0, 0, 5);
		gbc_btnPromotion.gridx = 3;
		gbc_btnPromotion.gridy = 3;
		add(btnPromotion, gbc_btnPromotion);
		btnPromotion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				main.setContentPane(new Promotion(main,user,al));
				main.revalidate();
			}
		});



		JButton logOffButton = new JButton("Log Off");
		GridBagConstraints gbc_logOffButton = new GridBagConstraints();
		gbc_logOffButton.insets = new Insets(0, 0, 0, 5);
		gbc_logOffButton.gridx = 1;
		gbc_logOffButton.gridy = 3;
		add(logOffButton, gbc_logOffButton);
		logOffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
				main.setContentPane(new Login(main, al));
				main.revalidate();
			}
		});
	}

}
