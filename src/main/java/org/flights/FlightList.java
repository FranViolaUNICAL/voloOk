package org.flights;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.SimpleDate;
import org.components.observers.AbstractSubject;
import org.components.singletons.ObjectMapperSingleton;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FlightList extends AbstractSubject {
    private static FlightList instance;
    private static List<Flight> flightList;

    private FlightList(){
        super();
        flightList = new ArrayList<>();
        try{
            ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
            Map<String,Object> map = mapper.readValue(new File("src/flightDatabase.json"),new TypeReference<Map<String,Object>>(){});
            List mainMap2 = (List) map.get("flightList");
            for (Object o : mainMap2) {
                String departureTime = (String) ((Map) o).get("departureTime");
                String arrivalTime = (String) ((Map) o).get("arrivalTime");
                String origin = (String) ((Map) o).get("origin");
                String destination = (String) ((Map) o).get("destination");
                String flightId = (String) ((Map) o).get("flightId");
                int price = (int) ((Map) o).get("price");
                int availableSeats = (int) ((Map) o).get("availableSeats");
                Flight f = new Flight(departureTime, arrivalTime, origin, destination, flightId, price, availableSeats);
                flightList.add(f);
            }
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

    @JsonProperty
    public List<Flight> getFlightList() {
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
        for(Flight flight : flightList) {
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
        for(Flight flight : flightList) {
            if(flight.getFlightId().equals(flightId)) {
                flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            }
        }
        notifyObservers();
    }

    public boolean hasFlightHappened(String flightId) {
        boolean hasAlreadyHappened = false;
        try{
            for(Flight flight : flightList) {
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
