package org.serverSide.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.serverSide.components.factories.SingletonListsFactory;
import org.serverSide.components.units.Flight;
import org.serverSide.components.units.Unit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightList extends SingletonListAbstract {
    private static FlightList instance;
    private FlightList(){
        super();
        try{
            list = SingletonListsFactory.createSingletonList("src/flightDatabase.json","flightList");
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
    public synchronized List<Unit> getFlightList() {
        return new ArrayList<>(list);
    }

    private synchronized void removeFlightFromFlightID(String flightID){
        for(Unit uf : list){
            Flight f = (Flight) uf;
            if(f.getFlightId().equals(flightID)){
                remove(uf);
            }
        }
    }

    public void remove(String flightID){
        removeFlightFromFlightID(flightID);
    }

    public List<Flight> checkAvailabilityOriginDestination(String origin, String destination) {
        List<Flight> l = new ArrayList<>();
        if(list.isEmpty()) {
            throw new RuntimeException("Initialize flight list singleton first.");
        }
        for(Unit u : list) {
            Flight flight = (Flight) u;
            if(flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)) {
                l.add(flight);
            }
        }
        return l;
    }

    public List<Flight> checkAvailabilityFromDate(String origin, String destination, String departureDate) throws ParseException {
        List<Flight> l = checkAvailabilityOriginDestination(origin, destination);
        for(Flight flight : l) {
            Date flightDate = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss").parse(flight.getDepartureTime());
            if(flightDate.before(new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss").parse(departureDate))){
                l.remove(flight);
            }
        }
        return l;
    }

    public synchronized void modifyFlight(Flight flight, String flightId) {
        if(!flight.getFlightId().equals(flightId)) {
            throw new IllegalArgumentException("Flight id does not match.");
        }
        list.remove(flight);
        add(flight);
    }

    public synchronized void occupySeat(String flightId){
        for(Unit u : list) {
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
            for(Unit u : list) {
                Flight flight = (Flight) u;
                if(flight.getFlightId().equals(flightId)) {
                    Date departureDate = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss").parse(flight.getDepartureTime());
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
