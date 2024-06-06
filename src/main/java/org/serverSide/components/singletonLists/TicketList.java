package org.serverSide.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.serverSide.components.factories.SingletonListsFactory;
import org.serverSide.components.units.Ticket;
import org.serverSide.components.units.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketList extends SingletonListAbstract {
    private static TicketList instance;
    private TicketList() {
        super();
        try{
            list = SingletonListsFactory.createSingletonList("src/ticketDatabase.json","ticketList");

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
        return new ArrayList<>(list);
    }

    public void add(Ticket ticket) {
        list.add(ticket);
        notifyObservers();
    }

    public void remove(Ticket ticket) {
        list.remove(ticket);
        notifyObservers();
    }
}
