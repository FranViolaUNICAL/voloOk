package org.serverSide.components.threads;

import org.serverSide.components.singletonLists.*;
import org.serverSide.components.units.*;
import org.serverSide.grpc.EmailService;
import org.serverSide.grpc.ThreadPoolManager;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanupManager {

    EmailService e = new EmailService("smtp-mail.outlook.com",587);

    public void deleteTickets(){
        ThreadPoolManager.getExecutorService().execute(() -> {
            ;
            TicketList ticketList = TicketList.getInstance();
            List<Unit> tickets = ticketList.getTicketList();
            FlightList flightList = FlightList.getInstance();
            List<Unit> flights = flightList.getFlightList();
            for (Unit uT : tickets) {
                Ticket ticket = (Ticket) uT;
                String flightId = ticket.getFlightId();
                boolean found = false;
                for (Unit uF : flights) {
                    Flight flight = (Flight) uF;
                    if (flight.getFlightId().equals(flightId)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ticketList.remove(ticket);
                }
            }
        });
    }

    public void deleteFlights(){
        ThreadPoolManager.getExecutorService().execute(() -> {
            ;
            FlightList flightList = FlightList.getInstance();
            List<Unit> flights = flightList.getFlightList();
            for (Unit u : flights) {
                Flight flight = (Flight) u;
                String flightId = flight.getFlightId();
                if (flightList.hasFlightHappened(flightId)) {
                    flightList.remove(flight);
                }
            }
        });
    }
    public void deletePromos(){
        ThreadPoolManager.getExecutorService().execute(()-> {
            ;
            List<Unit> promoList = PromoList.getInstance().getPromoList();
            try {
                for (Unit uP : promoList) {
                    Promo p = (Promo) uP;
                    String endDate = p.getEndDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
                    Date endDateParsed = sdf.parse(endDate);
                    Date currentDate = new Date();
                    if (currentDate.after(endDateParsed)) {
                        PromoList.getInstance().remove(uP);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
    public void deleteBooking(){
        ThreadPoolManager.getExecutorService().execute(()-> {
            ;
            try {
                List<Unit> bookingList = BookingList.getInstance().getBookingList();
                for (Unit uB : bookingList) {
                    Booking b = (Booking) uB;
                    String flightId = b.getFlightId();
                    List<Unit> l = FlightList.getInstance().getFlightList();
                    for (Unit u : l) {
                        Flight f = (Flight) u;
                        if (f.getFlightId().equals(flightId)) {
                            long differenceInDays = getDifferenceInDays(f);
                            if(differenceInDays == 4){
                                e.sendBookingEmail(b.getEmail(), b.getBookingId());
                            }
                            if (differenceInDays < 3) {
                                BookingList.getInstance().remove(uB);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
        ThreadPoolManager.getExecutorService().execute(() -> {
            try {
                List<Unit> userList = UserList.getInstance().getUserList();
                for (Unit uU : userList) {
                    User u = (User) uU;
                    String lastPurchase = u.getLastPurchaseDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
                    Date lastPurchaseDate = sdf.parse(lastPurchase);
                    Date currentDate = new Date();
                    long timeDifference = currentDate.getTime() - lastPurchaseDate.getTime();
                    System.out.println(timeDifference);
                    System.out.println(timeDifference / (1000L * 60 * 60 * 24));
                    if (((currentDate.getTime() - lastPurchaseDate.getTime()) / (1000L * 60 * 60 * 24) >= 730)) {
                        System.out.println("yes");
                        UserList.getInstance().addFidelityPoints(u, -(u.getFidelityPoints()));
                    }else if(((currentDate.getTime() - lastPurchaseDate.getTime()) /(1000L * 60 * 60 * 24) == 728)){
                        e.sendUnloyaltyEmail(u.getEmail());
                    }
                }
                UserList.getInstance().notifyObservers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void cleanup() throws InterruptedException {
        deleteBooking();
        deductFidelityPoints();
        deletePromos();
        deleteTickets();
        deleteFlights();
    }




}
