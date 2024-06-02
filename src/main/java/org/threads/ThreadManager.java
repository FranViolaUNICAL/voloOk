package org.threads;

import org.components.units.Flight;
import org.components.singletonLists.FlightList;
import org.components.units.Ticket;
import org.components.singletonLists.TicketList;
import org.components.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadManager{

    public void deleteTickets(){
        try{
            TicketList ticketList = TicketList.getInstance();
            List<Unit> tickets = ticketList.getTicketList();
            FlightList flightList = FlightList.getInstance();
            List<Unit> flights = flightList.getFlightList();
            for(Unit uT : tickets){
                Ticket ticket = (Ticket) uT;
                String flightId = ticket.getFlightId();
                boolean found = false;
                for(Unit uF : flights){
                    Flight flight = (Flight) uF;
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
            List<Unit> flights = flightList.getFlightList();
            for(Unit u : flights){
                Flight flight = (Flight) u;
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
