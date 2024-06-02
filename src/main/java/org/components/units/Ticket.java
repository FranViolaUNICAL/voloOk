package org.components.units;

import org.components.utils.RandomStringGenerator;

import java.util.Objects;

public class Ticket implements Unit{
    public final int IDLENGTH = 10;
    private String ticketId;
    private String flightId;
    private String passengerName;
    private String passengerEmail;

    public Ticket(String flightId, String passengerName, String passengerSurname, String passengerEmail) {
        this.ticketId = RandomStringGenerator.generateRandomString(IDLENGTH);
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
    }

    public Ticket(String ticketId, String flightId, String passengerName, String passengerSurname, String passengerEmail) {
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
    }

    public int getIDLENGTH() {
        return IDLENGTH;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
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
}
