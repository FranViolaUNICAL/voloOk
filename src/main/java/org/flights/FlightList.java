package org.flights;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class FlightList {
    private static FlightList instance;
    private static List<Flight> list;

    private FlightList() {
    }

    public static FlightList getInstance() {
        if(instance == null) {
            instance = new FlightList();
            list = new ArrayList<>();
        }
        return instance;
    }

    @JsonProperty
    public static List<Flight> getFlightList() {
        return new ArrayList<>(list);
    }

    public static void add(Flight flight) {
        list.add(flight);
    }

    public static void remove(Flight flight) {
        list.remove(flight);
    }
}
