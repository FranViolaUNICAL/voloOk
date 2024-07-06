package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.units.Flight;

import javax.swing.*;

public class AddFlightPage extends JFrame {
    private JTextField departureTimeField;
    private JTextField arrivalTimeField;
    private JTextField departureField;
    private JTextField destinationField;
    private JTextField flightIDField;
    private JTextField numSeatsField;
    private JTextField priceField;
    private JButton createFlightButton;
    private JPanel contentPane;

    public AddFlightPage(){
        setTitle("Add Flight");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();
        createFlightButton.addActionListener(e -> addFlight());
        setVisible(true);
    }

    private void addFlight(){
        String departureTime = departureTimeField.getText();
        String arrivalTime = arrivalTimeField.getText();
        String origin = departureField.getText();
        String destination = destinationField.getText();
        String flightID = flightIDField.getText();
        int num = Integer.parseInt(numSeatsField.getText());
        int price = Integer.parseInt(priceField.getText());
        Flight f = new Flight(departureTime, arrivalTime, origin, destination, flightID, price, num);
        FlightList.getInstance().add(f);
        setVisible(false);
    }
}
