package org.threads;

import org.flights.Flight;
import org.flights.FlightList;
import org.tickets.Ticket;
import org.tickets.TicketList;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TicketDeleter extends Thread{

    private ThreadManager tm;

    public TicketDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run(){
        tm.deleteTickets();
    }
}
