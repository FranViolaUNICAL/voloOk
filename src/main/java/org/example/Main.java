package org.example;
import org.components.singletonLists.BookingList;
import org.components.singletonLists.FlightList;
import org.components.singletonLists.TicketList;
import org.components.singletonLists.UserList;
import org.components.units.Ticket;
import org.components.units.Unit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Unit> ticketList = TicketList.getInstance().getTicketList();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------------------------------------");
        List<Unit> bookingList = BookingList.getInstance().getBookingList();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------------------------------------");
        List<Unit> userList = UserList.getInstance().getUserList();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------------------------------------");
        List<Unit> flightList = FlightList.getInstance().getFlightList();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------------------------------------");
        System.out.println(ticketList);
        System.out.println(bookingList);
        System.out.println(userList);
        System.out.println(flightList);

    }
}