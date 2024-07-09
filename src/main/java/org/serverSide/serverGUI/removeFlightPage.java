package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.FlightList;

import javax.swing.*;

public class removeFlightPage extends JFrame {
    private JPanel contentPane;

    private JTextField removeFlightField;
    private JButton removeFlightButton;


    public removeFlightPage(){
        setContentPane(contentPane);
        setTitle("VoloOk Server Interface");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        removeFlightButton.addActionListener(e -> removeFlight());

        setVisible(true);
    }

    private void removeFlight(){
        String flightID = removeFlightField.getText();
        FlightList.getInstance().remove(flightID);
        setVisible(false);
    }

}
