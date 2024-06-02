package org.threads;

import org.flights.Flight;
import org.flights.FlightList;
import org.tickets.Ticket;
import org.tickets.TicketList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadManager{

    public void deleteTickets(){
        try{
            TicketList ticketList = TicketList.getInstance();
            List<Ticket> tickets = ticketList.getTickets();
            FlightList flightList = FlightList.getInstance();
            List<Flight> flights = flightList.getFlightList();
            for(Ticket ticket : tickets){
                String flightId = ticket.getFlightId();
                boolean found = false;
                for(Flight flight : flights){
                    if(flight.getFlightId().equals(flightId)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    ticketList.remove(ticket);
                }
            }
            TimeUnit.DAYS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteFlights(){
        try{
            FlightList flightList = FlightList.getInstance();
            List<Flight> flights = flightList.getFlightList();
            for(Flight flight : flights){
                String flightId = flight.getFlightId();
                if(flightList.hasFlightHappened(flightId)){
                    flightList.remove(flight);
                }
            }
            TimeUnit.DAYS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void cleanup(){
        List<Thread> l = new ArrayList<>();
        l.add(new FlightDeleter(this));
        l.add(new TicketDeleter(this));
        for(Thread t : l){
            t.start();
        }
    }




}
