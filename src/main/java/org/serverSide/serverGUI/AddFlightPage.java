package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.units.Flight;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddFlightPage extends JFrame {
    private JTextField departureTimeField;

    private JTextField arrivalTimeField;
    private JPanel contentPane;

    private JTextField departureField;
    private JTextField destinationField;
    private JTextField flightIDField;
    private JTextField numSeatsField;
    private JTextField priceField;
    private JButton createFlightButton;
    private JLabel errorLabel;


    public AddFlightPage(){
        setContentPane(contentPane);
        setTitle("Add Flight");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        boolean isDateFormattedCorrectly = true;
        try{
            sdf.parse(departureTime);
            sdf.parse(arrivalTime);
        }catch (ParseException e){
            errorLabel.setVisible(true);
            isDateFormattedCorrectly = false;
        }
        if(departureTime.isBlank() || arrivalTime.isBlank() || origin.isBlank() || destination.isBlank() || flightID.isBlank() || numSeatsField.getText().isBlank() ||priceField.getText().isBlank() || !isDateFormattedCorrectly){
            errorLabel.setVisible(true);
        }
        else{
            Flight f = new Flight(departureTime, arrivalTime, origin, destination, flightID, price, num);
            FlightList.getInstance().add(f);
            setVisible(false);
        }
    }
}
