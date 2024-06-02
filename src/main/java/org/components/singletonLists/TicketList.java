package org.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.factories.SingletonListsFactory;
import org.components.observers.AbstractSubject;
import org.components.singletons.ObjectMapperSingleton;
import org.components.units.Ticket;
import org.components.units.Unit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketList extends AbstractSubject implements SingletonList {

    private List<Unit> tickets;
    private static TicketList instance;

    private TicketList() {
        super();
        try{
            tickets = SingletonListsFactory.createSingletonList("src/ticketDatabase.json","ticketList");

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

    @JsonProperty("ticketList")
    public List<Unit> getTickets() {
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
