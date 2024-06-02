package org.components.observers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.singletons.ObjectMapperSingleton;
import org.components.units.Flight;
import org.components.singletonLists.FlightList;
import org.components.singletonLists.TicketList;
import org.components.units.Unit;
import org.components.units.User;
import org.components.singletonLists.UserList;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public static boolean checkForFidelity(String userEmail, String flightId) throws IOException{
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
                return true;
            }
        }
        return false;
    }

}
