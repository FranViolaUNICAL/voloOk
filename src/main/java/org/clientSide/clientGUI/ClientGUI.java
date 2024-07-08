package org.clientSide.clientGUI;

import org.clientSide.Client;
import user.UserServices;

import javax.swing.*;

public class ClientGUI extends JFrame{
    private JPanel contentPane;
    protected JButton loginButton; // DONE
    protected JButton registerButton; // DONE
    protected JButton myBookingsButton; // DONE
    protected JButton myTicketsButton; // DONE
    private JButton bookFlightButton;
    private JButton buyTicket;
    private JButton searchFlightButton;
    private JButton promotionsButton; // DONE
    private JTextArea textArea;
    protected JButton changeBookingDateButton;

    private Client c;

    public ClientGUI(Client c){
        setTitle("VoloOk Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();
        this.c = c;

        loginButton.addActionListener(e -> { login();});
        registerButton.addActionListener(e -> register());
        myTicketsButton.addActionListener(e -> myTickets());
        myBookingsButton.addActionListener(e -> myBookings());
        promotionsButton.addActionListener(e -> fetchPromotions());
        buyTicket.addActionListener(e -> buyTicket());

        setVisible(true);
    }

    private void login(){
        if(loginButton.getText().equals("Login")){
            new LoginPage(this.c,this);
        }else{
            c.logout();
            loginButton.setText("Login");
        }
    }

    private void register(){
        new RegisterForm(this.c);
    }

    private void myBookings(){
        UserServices.FetchAllBookingsResponse response = c.fetchMyBookings(c.getEmail());
        StringBuilder sb = new StringBuilder();
        for(UserServices.Booking b : response.getBookingsList()){
            sb.append(b.getBookingId()).append(" ").append(b.getFlightId()).append('\n');
        }
        textArea.setText(sb.toString());
    }

    private void myTickets(){
        UserServices.FetchAllTicketsResponse response = c.fetchMyTickets(c.getEmail());
        StringBuilder sb = new StringBuilder();
        for(UserServices.Ticket t : response.getTicketsList()){
            sb.append(t.getTicketId()).append(" ").append(t.getFlightId()).append('\n');
        }
        textArea.setText(sb.toString());
    }

    private void bookFlight(){

    }

    private void searchFlight(){

    }

    private void buyTicket(){
        new BuyTicketForm(this.c);
    }

    private void fetchPromotions(){
        if(c.promos.isEmpty()){
            c.sendNotifications(c.isLoggedIn);
        }
        System.out.println(c.promos);
        textArea.setText("");
        for(String description : c.promos.keySet()){
            textArea.append(description);
        }
    }
}
