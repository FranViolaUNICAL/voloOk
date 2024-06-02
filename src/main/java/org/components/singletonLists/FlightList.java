package org.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.factories.SingletonListsFactory;
import org.components.observers.AbstractSubject;
import org.components.singletons.ObjectMapperSingleton;
import org.components.units.Flight;
import org.components.units.Unit;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FlightList extends AbstractSubject implements SingletonList {
    private static FlightList instance;
    private static List<Unit> flightList;

    private FlightList(){
        super();
        try{
            flightList = SingletonListsFactory.createSingletonList("src/flightDatabase.json","flightList");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized static FlightList getInstance() {
        if(instance == null) {
            instance = new FlightList();
        }
        return instance;
    }

    @JsonProperty("flightList")
    public List<Unit> getFlightList() {
        return new ArrayList<>(flightList);
    }

    public void add(Flight flight) {
        flightList.add(flight);
        notifyObservers();
    }

    public void remove(Flight flight) {
        flightList.remove(flight);
        notifyObservers();
    }

    public List<Flight> checkAvailabilityOriginDestination(String origin, String destination) {
        List<Flight> l = new ArrayList<>();
        if(flightList.isEmpty()) {
            throw new RuntimeException("Initialize flight list singleton first.");
        }
        for(Unit u : flightList) {
            Flight flight = (Flight) u;
            if(flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)) {
                l.add(flight);
            }
        }
        return l;
    }

    public List<Flight> checkAvailabilityFromDate(String origin, String destination, String date) {
        List<Flight> l = checkAvailabilityOriginDestination(origin, destination);
        for(Flight flight : l) {
            Date flightDate = new Date(flight.getDepartureTime());
            if(flightDate.compareTo(new Date(date)) < 0){
                l.remove(flight);
            }
        }
        return l;
    }

    public void modifyFlight(Flight flight, String flightId) {
        if(!flight.getFlightId().equals(flightId)) {
            throw new IllegalArgumentException("Flight id does not match.");
        }
        flightList.remove(flight);
        add(flight);
    }

    public void occupySeat(String flightId){
        for(Unit u : flightList) {
            Flight flight = (Flight) u;
            if(flight.getFlightId().equals(flightId)) {
                flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            }
        }
        notifyObservers();
    }

    public boolean hasFlightHappened(String flightId) {
        boolean hasAlreadyHappened = false;
        try{
            for(Unit u : flightList) {
                Flight flight = (Flight) u;
                if(flight.getFlightId().equals(flightId)) {
                    Date departureDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(flight.getDepartureTime());
                    if(departureDate.before(new Date())){
                        hasAlreadyHappened = true;
                    }
                    break;
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return hasAlreadyHappened;
    }
}
