package org.clientSide.clientGUI;

import org.clientSide.Client;
import user.UserServices;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class CancelBookingForm extends JFrame {
    private JComboBox<String> bookingIdCombo;
    private JButton cancelBookingButton;
    private JLabel errorLabel;
    private JPanel contentPane;

    private Client c;
    private Map<String,String> idMap = new HashMap<>();


    public CancelBookingForm(Client c){
        setContentPane(contentPane);
        this.c = c;
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    private void populateBookingIdCombo(){
        UserServices.FetchAllBookingsResponse response = c.fetchMyBookings(c.getEmail());
        for(UserServices.Booking b : response.getBookingsList()){
            bookingIdCombo.addItem(b.getBookingId());
            idMap.put(b.getBookingId(),b.getFlightId());
        }
    }

    private void cancelBooking(){
        String bookingId = (String) bookingIdCombo.getSelectedItem();
        if(c.cancelBooking(c.getEmail(),idMap.get(bookingId))){
            setVisible(false);
        }else{
            errorLabel.setVisible(true);
        }

    }
}
