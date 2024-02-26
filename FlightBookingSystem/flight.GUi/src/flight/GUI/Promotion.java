package flight.GUI;

import java.awt.EventQueue;

import java.util.ArrayList;

import javax.swing.*;

import entity.Aircraft;
import entity.Airline;
import entity.Flight;
import entity.Seat;

import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Promotion extends JPanel {
    private JTextField textField_1;
    public Promotion(JFrame main, String user, Airline al){
        setLayout(null);

        JLabel lblNewLabel = new JLabel("<html><div style='text-align: center;'>You can get 30% off our lounge!</div></html>", SwingConstants.CENTER);
        lblNewLabel.setBounds(200, 5, 207, 37);
        add(lblNewLabel);

        JButton btnNewButton = new JButton("Home Page");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent E) {
                main.setContentPane(new Customer(main, user, al));
                main.revalidate();
            }

        });
        btnNewButton.setBounds(250, 200, 117, 29);
        add(btnNewButton);

        textField_1 = new JTextField();
        textField_1.setBounds(200, 79, 200, 30);
        add(textField_1);
        textField_1.setColumns(10);
        JButton btnNewButton1 = new JButton("Apply for Credit Card");
        btnNewButton1.setBounds(200, 120, 200, 20);
        add(btnNewButton1);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent E) {
                String email = textField_1.getText();
                if (email.equals(null)) {
                    JLabel error = new JLabel("Application Error");
                    lblNewLabel.setBounds(150, 20, 207, 37);
                    add(error);

                }
                else{
                    JLabel app = new JLabel("Application Sent");
                    lblNewLabel.setBounds(150, 20, 207, 37);
                    add(app);
                }

            };



        });


    }




}
