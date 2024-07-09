package org.clientSide.clientGUI;

import org.clientSide.Client;
import user.*;

import javax.swing.*;

public class ClientGUI extends JFrame{
    protected JButton loginButton; // DONE
    protected JButton registerButton; // DONE
    protected JButton myBookingsButton; // DONE
    protected JButton myTicketsButton; // DONE
    private JButton bookFlightButton; // DONE
    private JButton buyTicket; // DONE
    private JButton searchFlightButton; // DONE
    private JPanel contentPane;

    private JButton promotionsButton; // DONE
    protected JTextArea textArea;
    protected JButton changeBookingDateButton; // DONE
    protected JButton cancelBookingButton;
    protected JButton myFidelityPointsButton;


    private Client c;

    public ClientGUI(Client c){
        setContentPane(contentPane);
        setTitle("VoloOk Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        this.c = c;

        loginButton.addActionListener(e -> { login();});
        registerButton.addActionListener(e -> register());
        myTicketsButton.addActionListener(e -> myTickets());
        myBookingsButton.addActionListener(e -> myBookings());
        promotionsButton.addActionListener(e -> fetchPromotions());
        buyTicket.addActionListener(e -> buyTicket());
        bookFlightButton.addActionListener(e -> bookFlight());
        searchFlightButton.addActionListener(e -> searchFlight());
        changeBookingDateButton.addActionListener( e -> changeBooking());
        cancelBookingButton.addActionListener(e -> cancelBooking());
        myFidelityPointsButton.addActionListener( e -> showFidelityPoints());
        setVisible(true);
    }

    private void login(){
        if(loginButton.getText().equals("Login")){
            new LoginPage(this.c,this);
        }else{
            c.logout();
            registerButton.setVisible(true);
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
        new BookFlightForm(this.c);
    }

    private void searchFlight(){
        textArea.setText("");
        new SearchFlightForm(this.c, this);
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

    private void changeBooking(){
        new ChangeBookingForm(this.c);
    }

    private void cancelBooking(){
        new CancelBookingForm(this.c);
    }

    private void showFidelityPoints(){
        textArea.setText("");
        textArea.append("You have earned " + c.getFidelityPoints() + " Fidelity points so far. Good job!");
    }

    public void connectionErrorMode(){
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
        buyTicket.setEnabled(false);
        bookFlightButton.setEnabled(false);
        promotionsButton.setEnabled(false);
        searchFlightButton.setEnabled(false);
        textArea.setText("Could not establish connection to server. Please close this application and restart later.");
    }
}
