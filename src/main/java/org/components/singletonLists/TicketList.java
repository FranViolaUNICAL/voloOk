package org.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.components.factories.SingletonListsFactory;
import org.components.units.Ticket;
import org.components.units.Unit;
import java.io.IOException;
import java.util.List;

public class TicketList extends SingletonListAbstract {

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
    public List<Unit> getTicketList() {
        return getAll();
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
