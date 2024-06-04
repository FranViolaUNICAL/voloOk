package org.components.observers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.singletonLists.BookingList;
import org.components.singletons.ObjectMapperSingleton;
import org.components.units.Booking;
import org.components.units.Flight;
import org.components.singletonLists.FlightList;
import org.components.singletonLists.TicketList;
import org.components.units.Unit;
import org.components.units.User;
import org.components.singletonLists.UserList;

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

    public static boolean checkCredentials(String email, String password) throws IOException {
        List<Unit> l = UserList.getInstance().getUserList();
        for(Unit u : l){
            User user = (User) u;
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkForTicketPurchase(String flightId) throws IOException{
        List<Unit> l = FlightList.getInstance().getFlightList();
        for(Unit uf : l){
            Flight f = (Flight) uf;
            if(f.getFlightId().equals(flightId) && f.getAvailableSeats() >= 1){
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
            if(user.getEmail().equals(userEmail)){
                List<Unit> lf = FlightList.getInstance().getFlightList();
                for(Unit uf : lf){
                    Flight f = (Flight) uf;
                    if(f.getFlightId().equals(flightId)){
                        points = f.getPrice()*2;
                    }
                }
                UserList.getInstance().addFidelityPoints(user,points);
                return;
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

    public static List<Flight> isDateChangePossible(String bookingId, String flightId, String newDateDeparture) throws ParseException {
        List<Unit> lBooking = BookingList.getInstance().getBookingList();
        for(Unit uB : lBooking){
            Booking b = (Booking) uB;
            if(b.getBookingId().equals(bookingId)){
                List<Unit> lFlights = FlightList.getInstance().getFlightList();
                for(Unit uF : lFlights){
                    Flight f = (Flight) uF;
                    if(f.getFlightId().equals(flightId)){
                        return FlightList.getInstance().checkAvailabilityFromDate(f.getOrigin(), f.getDestination(), newDateDeparture);
                    }
                    else{
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
                return newBooking.getBookingId();
            }
        }
        return null;
    }

}
