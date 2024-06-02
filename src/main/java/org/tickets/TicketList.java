package org.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.observers.AbstractSubject;
import org.components.observers.Observer;
import org.components.observers.Subject;
import org.components.singletons.ObjectMapperSingleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketList extends AbstractSubject {

    private List<Ticket> tickets;
    private static TicketList instance;

    private TicketList() {
        super();
        tickets = new ArrayList<>();
        try{
            ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
            Map<String,Object> map = mapper.readValue(new File("src/ticketDatabase.json"),new TypeReference<Map<String,Object>>(){});
            List mainMap2 = (List) map.get("ticketList");
            for(Object o : mainMap2) {
                String ticketId = (String) ((Map) o).get("tickedId");
                String flightId = (String) ((Map) o).get("flightId");
                String passengerName = (String) ((Map) o).get("passengerName");
                String passengerSurname = (String) ((Map) o).get("passengerSurname");
                String passengerEmail = (String) ((Map) o).get("passengerEmail");
                Ticket ticket = new Ticket(ticketId, flightId, passengerName, passengerEmail);
                tickets.add(ticket);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized static TicketList getInstance() {
        if (instance == null) {
            instance = new TicketList();
        }
        return instance;
    }

    @JsonProperty
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void add(Ticket ticket) {
        tickets.add(ticket);
        notifyObservers();
    }

    public void remove(Ticket ticket) {
        tickets.remove(ticket);
        notifyObservers();
    }
}
