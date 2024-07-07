package org.serverSide.serverGUI;
import org.serverSide.components.observers.JsonManagerObs;
import org.serverSide.components.observers.Observer;
import org.serverSide.components.observers.Subject;
import org.serverSide.components.singletonLists.BookingList;
import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.singletonLists.PromoList;
import org.serverSide.components.singletonLists.TicketList;
import org.serverSide.components.threads.CleanupManager;
import org.serverSide.components.units.*;

import javax.swing.*;
import java.util.List;

public class ServerGUI extends JFrame implements Observer {
    private JPanel contentPane;
    private JButton promoCleanupButton;
    private JButton flightCleanupButton;
    private JButton ticketCleanupButton;
    private JButton addPromotionButton;
    private JButton addFlightButton;
    private JButton bookingCleanupButton;
    private JScrollPane flightsScrollPane;
    private JTextArea flightTextArea;
    private JLabel flightLabel;
    private JLabel promoLabel;
    private JScrollPane promoScrollPane;
    private JTextArea promoTextArea;
    private JLabel ticketLabel;
    private JScrollPane ticketScrollPane;
    private JTextArea ticketTextArea;
    private JScrollPane bookingScrollpane;
    private JLabel bookingLabel;
    private JTextArea bookingTextArea;
    private JTextField searchFlightTextField;
    private JButton searchFlightButton;
    private JTextField searchPromoTextField;
    private JTextField searchTicketTextField;
    private JTextField searchBookingTextField;
    private JButton searchPromoButton;
    private JButton searchTicketButton;
    private JButton searchBookingButton;
    private JButton removeFlightButton;
    private JButton removePromotionButton;

    public ServerGUI(){
        setTitle("VoloOk Server Interface");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        flightCleanupButton.addActionListener(e -> {new CleanupManager().deleteFlights(); populateFlights();});
        bookingCleanupButton.addActionListener(e -> {new CleanupManager().deleteBooking(); populateBookings();});
        promoCleanupButton.addActionListener(e -> {new CleanupManager().deletePromos(); populatePromos();});
        ticketCleanupButton.addActionListener(e -> {new CleanupManager().deleteTickets(); populateTickets();});
        searchTicketButton.addActionListener(e -> searchTicket());
        searchBookingButton.addActionListener(e -> searchBooking());
        searchPromoButton.addActionListener(e -> searchPromo());
        searchFlightButton.addActionListener(e -> searchFlight());
        addFlightButton.addActionListener(e -> {addFlight(); populateFlights();});
        removeFlightButton.addActionListener(e -> {removeFlight(); populateFlights();});
        addPromotionButton.addActionListener(e -> {addPromo(); populatePromos();});



        populateBookings();
        populateFlights();
        populatePromos();
        populateTickets();

        setVisible(true);
    }

    private void addPromo() {
        new AddPromoPage();
    }

    private void populateFlights(){
        flightTextArea.setText("");
        List<Unit> flightList = FlightList.getInstance().getFlightList();
        for(Unit uF : flightList){
            Flight f = (Flight) uF;
            flightTextArea.append(f.toString()+ '\n');
        }
    }

    private void populateBookings(){
        bookingTextArea.setText("");
        List<Unit> bookingList = BookingList.getInstance().getBookingList();
        for(Unit uB: bookingList){
            Booking b = (Booking) uB;
            bookingTextArea.append(b.toString() + '\n');
        }
    }

    private void populateTickets(){
        ticketTextArea.setText("");
        List<Unit> ticketList = TicketList.getInstance().getTicketList();
        for(Unit uT : ticketList){
            Ticket t = (Ticket) uT;
            ticketTextArea.append(t.toString()+ '\n');
        }
    }

    private void populatePromos(){
        promoTextArea.setText("");
        List<Unit> promoList = PromoList.getInstance().getPromoList();
        for(Unit uP : promoList){
            Promo p = (Promo) uP;
            promoTextArea.append(p.toString()+ '\n');
        }
    }

    private void searchTicket(){
        String ticketID = searchTicketTextField.getText();
        List<Unit> ticketList = TicketList.getInstance().getTicketList();
        boolean flag = false;
        for(Unit uT : ticketList){
            Ticket t = (Ticket) uT;
            if(t.getTicketId().equals(ticketID)){
                ticketTextArea.setText(t.toString()+ '\n');
                flag = true;
            }
        }
        if(!flag){
            ticketTextArea.setText("Could not find ticket.");
        }
    }
    private void searchPromo(){
        String promoCode = searchPromoTextField.getText();
        List<Unit> promoList = PromoList.getInstance().getPromoList();
        boolean flag = false;
        for(Unit uP : promoList){
            Promo p = (Promo) uP;
            if(p.getCode().equals(promoCode)){
                promoTextArea.setText(p.toString()+ '\n');
                flag = true;
            }
        }
        if(!flag){
            promoTextArea.setText("Could not find promo.");
        }
    }
    private void searchFlight(){
        String flightId = searchFlightTextField.getText();
        List<Unit> flightList = FlightList.getInstance().getFlightList();
        boolean flag = false;
        for(Unit uF : flightList){
            Flight f = (Flight) uF;
            if(f.getFlightId().equals(flightId)){
                flightTextArea.setText(f.toString()+ '\n');
                flag = true;
            }
        }
        if(!flag){
            flightTextArea.setText("Could not find flight.");
        }
    }
    private void searchBooking(){
        String bookingId = searchBookingTextField.getText();
        List<Unit> bookingList = BookingList.getInstance().getBookingList();
        boolean flag = false;
        for(Unit uB : bookingList){
            Booking b = (Booking) uB;
            if(b.getBookingId().equals(bookingId)){
                bookingTextArea.setText(b.toString()+ '\n');
                flag = true;
            }
        }
        if(!flag){
            bookingTextArea.setText("Could not find booking.");
        }
    }

    private void addFlight(){
        new AddFlightPage();
    }

    private void removeFlight(){
        new removeFlightPage();
    }

    @Override
    public void update(Subject subject) {
        if(subject.getClass() == JsonManagerObs.class){
            populateBookings();
            populateTickets();
        }
    }
}
