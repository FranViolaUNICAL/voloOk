package org.flights;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class FlightList {
    private static FlightList instance;
    private static List<Flight> list;

    private FlightList() {
        list = new ArrayList<>();
    }

    public static FlightList getInstance() {
        if(instance == null) {
            instance = new FlightList();
        }
        return instance;
    }

    @JsonProperty
    public static List<Flight> getFlightList() {
        return new ArrayList<>(list);
    }

    public void add(Flight flight) {
        list.add(flight);
    }

    public void remove(Flight flight) {
        list.remove(flight);
    }
}
