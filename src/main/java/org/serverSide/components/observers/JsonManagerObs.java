package org.serverSide.components.observers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.serverSide.components.singletonLists.*;
import org.serverSide.components.singletons.ObjectMapperSingleton;
import org.serverSide.components.units.*;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonManagerObs implements Observer{
    public static final File USERJSON = new File("src/userDatabase.json");
    public static final File FLIGHTJSON = new File("src/flightDatabase.json");
    public static final File TICKETJSON = new File("src/ticketDatabase.json");
    public static final File BOOKINGJSON = new File("src/bookingDatabase.json");
    public static final File PROMOJSON = new File("src/promoDatabase.json");

    @Override
    public synchronized void update(Subject subject){
        try{
            if(subject.getClass() == UserList.class){
                UserList userList = (UserList) subject;
                ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(USERJSON, userList);
            }
            else if(subject.getClass() == FlightList.class){
                FlightList flightList = (FlightList) subject;
                ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(FLIGHTJSON, flightList);
            }
            else if(subject.getClass() == TicketList.class){
                TicketList ticketList = (TicketList) subject;
                ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(TICKETJSON, ticketList);
            }
            else if(subject.getClass() == BookingList.class){
                BookingList bookingList = (BookingList) subject;
                ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(BOOKINGJSON, bookingList);
            }
            else if(subject.getClass() == PromoList.class){
                PromoList promolist = (PromoList) subject;
                ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(PROMOJSON, promolist);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean checkJsonForEmail(String email) throws IOException {
        List<Unit> l = UserList.getInstance().getUserList();
        for(Unit u : l){
            User user = (User) u;
            if(user.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    public static User checkCredentials(String email, String password) throws IOException {
        List<Unit> l = UserList.getInstance().getUserList();
        for(Unit u : l){
            User user = (User) u;
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public static boolean checkForTicketPurchase(String flightId, String cardNumber, String email) throws IOException{
        List<Unit> l = FlightList.getInstance().getFlightList();
        List<Unit> lB = BookingList.getInstance().getBookingList();
        for(Unit uf : l){
            Flight f = (Flight) uf;
            if(f.getFlightId().equals(flightId) && f.getAvailableSeats() >= 1 && checkLuhn(cardNumber)){
                for(Unit uB : lB){
                    Booking b = (Booking) uB;
                    if(b.getEmail().equals(email) && b.getFlightId().equals(flightId)){
                        BookingList.getInstance().remove(uB);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static void checkForFidelity(String userEmail, String flightId) throws IOException{
        List<Unit> l = UserList.getInstance().getUserList();
        int points = 0;
        for(Unit u : l){
            User user = (User) u;
            if(user.getEmail().equals(userEmail)) {
                List<Unit> lf = FlightList.getInstance().getFlightList();
                for (Unit uf : lf) {
                    Flight f = (Flight) uf;
                    if (f.getFlightId().equals(flightId)) {
                        points = f.getPrice() * 2;
                    }
                }
                UserList.getInstance().addFidelityPoints(user, points);
                user.setLastPurchaseDate(new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss").format(new Date()));
                UserList.getInstance().notifyObservers();
            }
        }
    }

    public static boolean checkForBooking(String flightId, int numSeats, String email) throws IOException, ParseException {
        List<Unit> lBooking = BookingList.getInstance().getBookingList();
        for(Unit u : lBooking){
            Booking b = (Booking) u;
            if(b.getEmail().equals(email) && b.getFlightId().equals(flightId)){
                return false;
            }
        }
        List<Unit> l = FlightList.getInstance().getFlightList();
        for(Unit u : l){
            Flight f = (Flight) u;
            if(f.getFlightId().equals(flightId)){
                if(f.getAvailableSeats() >= numSeats){
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
                    Date departureDate = sdf.parse(f.getDepartureTime());
                    Date currentDate = new Date();
                    String currentDateString = sdf.format(currentDate);
                    currentDate = sdf.parse(currentDateString);
                    long difference = departureDate.getTime() - currentDate.getTime();
                    long differenceInDays = difference / (24 * 60 * 60 * 1000);
                    if(differenceInDays < 3){
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public static List<Flight> isDateChangePossible(String bookingId, String newDateDeparture) throws ParseException {
        List<Unit> lBooking = BookingList.getInstance().getBookingList();
        for(Unit uB : lBooking){
            Booking b = (Booking) uB;
            if(b.getBookingId().equals(bookingId)){
                List<Unit> lFlights = FlightList.getInstance().getFlightList();
                for(Unit uF : lFlights){
                    Flight f = (Flight) uF;
                    if(f.getFlightId().equals(b.getFlightId())) {
                        return FlightList.getInstance().checkAvailabilityFromDate(f.getOrigin(), f.getDestination(), newDateDeparture);
                    }else{
                        return new ArrayList<Flight>();
                    }
                }
            }
        }
        return new ArrayList<Flight>();
    }

    public static String changeBookingDate(String bookingId, String flightId){
        List<Unit> lBooking = BookingList.getInstance().getBookingList();
        for(Unit uB : lBooking){
            Booking b = (Booking) uB;
            if(b.getBookingId().equals(bookingId)){
                Booking newBooking = new Booking(flightId, b.getName(), b.getSurname(), b.getEmail(), b.getBookedTicketsNum());
                BookingList.getInstance().remove(uB);
                BookingList.getInstance().add(newBooking);
                return newBooking.getBookingId();
            }
        }
        return null;
    }

    public static boolean removeFlightBooking(String flightId, String email){
        List<Unit> lBooking = BookingList.getInstance().getBookingList();
        for(Unit uB : lBooking){
            Booking b = (Booking) uB;
            if(b.getFlightId().equals(flightId) && b.getEmail().equals(email)){
                BookingList.getInstance().remove(uB);
                return true;
            }
        }
        return false;
    }

    private static boolean checkLuhn(String cardNo)
    {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {
            int d = cardNo.charAt(i) - '0';
            if (isSecond == true)
                d = d * 2;
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}
