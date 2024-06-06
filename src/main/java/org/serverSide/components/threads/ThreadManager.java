package org.serverSide.components.threads;

import org.serverSide.components.singletonLists.BookingList;
import org.serverSide.components.singletonLists.UserList;
import org.serverSide.components.units.*;
import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.singletonLists.TicketList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThreadManager{

    public void deleteTickets(){
        TicketList ticketList = TicketList.getInstance();
        List<Unit> tickets = ticketList.getTicketList();
        FlightList flightList = FlightList.getInstance();
        List<Unit> flights = flightList.getFlightList();
        for(Unit uT : tickets){
            Ticket ticket = (Ticket) uT;
            String flightId = ticket.getFlightId();
            boolean found = false;
            for(Unit uF : flights){
                Flight flight = (Flight) uF;
                if(flight.getFlightId().equals(flightId)){
                    found = true;
                    break;
                }
            }
            if(!found){
                ticketList.remove(ticket);
            }
        }
    }

    public void deleteFlights(){
        FlightList flightList = FlightList.getInstance();
        List<Unit> flights = flightList.getFlightList();
        for(Unit u : flights){
            Flight flight = (Flight) u;
            String flightId = flight.getFlightId();
            if(flightList.hasFlightHappened(flightId)){
                flightList.remove(flight);
            }
        }
    }

    public void deleteBooking(){
        try{
            List<Unit> bookingList = BookingList.getInstance().getBookingList();
            for(Unit uB : bookingList){
                Booking b = (Booking) uB;
                String flightId = b.getFlightId();
                List<Unit> l = FlightList.getInstance().getFlightList();
                for(Unit u : l) {
                    Flight f = (Flight) u;
                    if (f.getFlightId().equals(flightId)) {
                        long differenceInDays = getDifferenceInDays(f);
                        if (differenceInDays < 3) {
                            BookingList.getInstance().remove(uB);
                        }
                    }
                }
            }
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    private static long getDifferenceInDays(Flight f) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        Date departureDate = sdf.parse(f.getDepartureTime());
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);
        currentDate = sdf.parse(currentDateString);
        long difference = departureDate.getTime() - currentDate.getTime();
        long differenceInDays = difference / (24 * 60 * 60 * 1000);
        return differenceInDays;
    }

    public void deductFidelityPoints(){
        try{
        List<Unit> userList = UserList.getInstance().getUserList();
        for(Unit uU : userList){
            User u = (User) uU;
            String lastPurchase = u.getLastPurchaseDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
            Date lastPurchaseDate = sdf.parse(lastPurchase);
            Date currentDate = new Date();
            if(((currentDate.getTime() - lastPurchaseDate.getTime()) / (1000L *60*60*24*365) >= 2)){
                UserList.getInstance().addFidelityPoints(u,-(u.getFidelityPoints()));
            }
        }
        UserList.getInstance().notifyObservers();
        }catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }


    public void cleanup(){
        List<Thread> l = new ArrayList<>();
        l.add(new FlightDeleter(this));
        l.add(new TicketDeleter(this));
        l.add(new BookingDeleter(this));
        l.add(new FidelityDeducter(this));
        for(Thread t : l){
            t.start();
        }
    }




}
