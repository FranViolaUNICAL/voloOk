package org.example;
import org.components.observers.JsonManagerObs;
import org.components.singletonLists.BookingList;
import org.components.singletonLists.FlightList;
import org.components.singletonLists.TicketList;
import org.components.singletonLists.UserList;
import org.components.units.*;
import org.components.units.Unit;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException{
        JsonManagerObs man = new JsonManagerObs();

        //Ticket t = new Ticket("SUFLIN1308","Francesco","Viola","franviola98");
        //Flight f = new Flight("13/Aug/2024 13:00:00", "13/Aug/2024 18:20:00", "SUF","LIN","SUFLIN1308",150,120);
        //Booking b = new Booking("SUFLIN1308","Chiara","De Santis","chiaradesantis@gmail.com",2);

        TicketList.getInstance().attach(man);
        BookingList.getInstance().attach(man);
        UserList.getInstance().attach(man);
        FlightList.getInstance().attach(man);

        //TicketList.getInstance().add(t);
        //BookingList.getInstance().add(b);
        //FlightList.getInstance().add(f);

        List<Unit> ticketList = TicketList.getInstance().getTicketList();
        List<Unit> bookingList = BookingList.getInstance().getBookingList();
        List<Unit> userList = UserList.getInstance().getUserList();
        List<Unit> flightList = FlightList.getInstance().getFlightList();

        System.out.println(ticketList);
        System.out.println("---------------------------------------------------------");
        System.out.println(flightList);
        System.out.println("---------------------------------------------------------");
        System.out.println(bookingList);
        System.out.println("---------------------------------------------------------");
        System.out.println(userList);


    }
}