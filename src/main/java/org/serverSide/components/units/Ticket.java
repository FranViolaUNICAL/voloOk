package org.serverSide.components.units;

import org.serverSide.components.utils.RandomStringGenerator;

import java.util.Objects;

public class Ticket implements Unit{
    private final String ticketId;
    private String flightId;
    private String passengerName;
    private String passengerSurname;
    private String passengerEmail;



    public Ticket(String flightId, String passengerName, String passengerSurname, String passengerEmail) {
        int IDLENGTH = 10;
        this.ticketId = RandomStringGenerator.generateRandomString(IDLENGTH);
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
        this.passengerEmail = passengerEmail;
    }

    public Ticket(String ticketId, String flightId, String passengerName, String passengerSurname, String passengerEmail) {
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
        this.passengerEmail = passengerEmail;
    }
    public String getTicketId() {
        return ticketId;
    }
    public String getFlightId() {
        return flightId;
    }
    public String getPassengerName() {
        return passengerName;
    }
    public String getPassengerSurname(){ return passengerSurname; }
    public String getPassengerEmail() {
        return passengerEmail;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return Objects.equals(ticketId, ticket.ticketId) && Objects.equals(flightId, ticket.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, flightId);
    }
    @Override
    public String toString() {
        return ticketId;
    }

}
