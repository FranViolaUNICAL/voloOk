package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.FlightList;

import javax.swing.*;

public class removeFlightPage extends JFrame {
    private JTextField removeFlightField;
    private JPanel contentPane;
    private JButton removeFlightButton;

    public removeFlightPage(){
        setTitle("VoloOk Server Interface");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
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
