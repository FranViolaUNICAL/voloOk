package org.flights;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.SimpleDate;
import org.components.ObjectMapperSingleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FlightList {
    private static FlightList instance;
    private static List<Flight> flightList;

    private FlightList() throws IOException {
        flightList = new ArrayList<>();
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
        Map<String,Object> map = mapper.readValue(new File("src/flightDatabase.json"),new TypeReference<Map<String,Object>>(){});
        List mainMap2 = (List) map.get("flightList");
        for(Object object : mainMap2) {
            for(int i = 0; i < mainMap2.size(); i++){
                String departureTime = (String)((Map)mainMap2.get(i)).get("departureTime");
                String arrivalTime = (String)((Map)mainMap2.get(i)).get("arrivalTime");
                String origin = (String)((Map)mainMap2.get(i)).get("origin");
                String destination = (String)((Map)mainMap2.get(i)).get("destination");
                String flightId = (String)((Map)mainMap2.get(i)).get("flightId");
                int price = (int)((Map)mainMap2.get(i)).get("price");
                int availableSeats = (int)((Map)mainMap2.get(i)).get("availableSeats");
                Flight f = new Flight(departureTime,arrivalTime,origin,destination,flightId,price,availableSeats);
                flightList.add(f);
            }
        }
    }

    public static FlightList getInstance() throws IOException {
        if(instance == null) {
            instance = new FlightList();
        }
        return instance;
    }

    @JsonProperty
    public static List<Flight> getFlightList() {
        return new ArrayList<>(flightList);
    }

    public void add(Flight flight) {
        flightList.add(flight);
    }

    public void remove(Flight flight) {
        flightList.remove(flight);
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
}
