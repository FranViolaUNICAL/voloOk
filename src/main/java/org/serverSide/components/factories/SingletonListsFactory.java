package org.serverSide.components.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.serverSide.components.singletons.ObjectMapperSingleton;
import org.serverSide.components.units.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingletonListsFactory {
    public static List<Unit> createSingletonList(String jsonPath, String JsonPropertyTag) throws IOException {
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
        Map<String, Object> map = mapper.readValue(new File(jsonPath), new TypeReference<>() {
        });
        List<?> mainMap2 = (List<?>) map.get(JsonPropertyTag);
        List<Unit> ret = new ArrayList<>();
        switch (JsonPropertyTag) {
            case "userList":
                for (Object o : mainMap2) {
                    String name = (String) ((Map<?, ?>) o).get("name");
                    String surname = (String) ((Map<?, ?>) o).get("surname");
                    String email = (String) ((Map<?, ?>) o).get("email");
                    String password = (String) ((Map<?, ?>) o).get("password");
                    String luogoDiNascita = (String) ((Map<?, ?>) o).get("luogoDiNascita");
                    String regioneDiNascita = (String) ((Map<?, ?>) o).get("regioneDiNascita");
                    String dataDiNascita = (String) ((Map<?, ?>) o).get("dataDiNascita");
                    User u = new User(name, surname, email, password, luogoDiNascita, regioneDiNascita, dataDiNascita);
                    ret.add(u);
                }
                break;
            case "flightList":
                for (Object o : mainMap2) {
                    String departureTime = (String) ((Map<?, ?>) o).get("departureTime");
                    String arrivalTime = (String) ((Map<?, ?>) o).get("arrivalTime");
                    String origin = (String) ((Map<?, ?>) o).get("origin");
                    String destination = (String) ((Map<?, ?>) o).get("destination");
                    String flightId = (String) ((Map<?, ?>) o).get("flightId");
                    String price = ((Map<?, ?>) o).get("price").toString();
                    String availableSeats = ((Map<?, ?>) o).get("availableSeats").toString();
                    int priceInt = price == null ? 0 : Integer.parseInt(price);
                    int availableSeatsInt = availableSeats == null ? 0 : Integer.parseInt(availableSeats);
                    Flight f = new Flight(departureTime, arrivalTime, origin, destination, flightId, priceInt, availableSeatsInt);
                    ret.add(f);
                }
                break;
            case "ticketList":
                for (Object o : mainMap2) {
                    String ticketId = (String) ((Map<?, ?>) o).get("ticketId");
                    String flightId = (String) ((Map<?, ?>) o).get("flightId");
                    String passengerName = (String) ((Map<?, ?>) o).get("passengerName");
                    String passengerSurname = (String) ((Map<?, ?>) o).get("passengerSurname");
                    String passengerEmail = (String) ((Map<?, ?>) o).get("passengerEmail");
                    Ticket t = new Ticket(ticketId, flightId, passengerName, passengerSurname, passengerEmail);
                    ret.add(t);
                }
                break;
            case "bookingList":
                for (Object o : mainMap2) {
                    String bookingId = (String) ((Map<?, ?>) o).get("bookingId");
                    String flightId = (String) ((Map<?, ?>) o).get("flightId");
                    String name = (String) ((Map<?, ?>) o).get("name");
                    String surname = (String) ((Map<?, ?>) o).get("surname");
                    String email = (String) ((Map<?, ?>) o).get("email");
                    String bookedTicketsNum = ((Map<?, ?>) o).get("bookedTicketsNum").toString();
                    int bookedTicketsNumInt = bookedTicketsNum == null ? 0 : Integer.parseInt(bookedTicketsNum);
                    Booking b = new Booking(bookingId, flightId, name, surname, email, bookedTicketsNumInt);
                    ret.add(b);
                }
                break;
        }
        return ret;
    }
}
