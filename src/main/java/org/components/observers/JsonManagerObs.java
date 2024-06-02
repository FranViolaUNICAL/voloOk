package org.components.observers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.singletons.ObjectMapperSingleton;
import org.flights.Flight;
import org.flights.FlightList;
import org.tickets.TicketList;
import org.users.User;
import org.users.UserList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class JsonManagerObs implements Observer{
    public static final File USERJSON = new File("src/userDatabase.json");
    public static final File FLIGHTJSON = new File("src/flightDatabase.json");
    public static final File TICKETJSON = new File("src/ticketDatabase.json");

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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean checkJsonForEmail(String email) throws IOException {
        List<User> l = UserList.getInstance().getUserList();
        for(User u : l){
            if(u.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    public static boolean checkCredentials(String email, String password) throws IOException {
        List<User> l = UserList.getInstance().getUserList();
        for(User u : l){
            if(u.getEmail().equals(email) && u.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkForTicketPurchase(String flightId) throws IOException{
        List<Flight> l = FlightList.getInstance().getFlightList();
        for(Flight f : l){
            if(f.getFlightId().equals(flightId) && f.getAvailableSeats() >= 1){
                return true;
            }
        }
        return false;
    }

    public static boolean checkForFidelity(String userEmail, String flightId) throws IOException{
        List<User> l = UserList.getInstance().getUserList();
        int points = 0;
        for(User u : l){
            if(u.getEmail().equals(userEmail)){
                List<Flight> lf = FlightList.getInstance().getFlightList();
                for(Flight f : lf){
                    if(f.getFlightId().equals(flightId)){
                        points = f.getPrice()*2;
                    }
                }
                UserList.getInstance().addFidelityPoints(u,points);
                return true;
            }
        }
        return false;
    }

}
