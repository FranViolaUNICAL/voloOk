package org.serverSide.components.units;

import org.serverSide.components.utils.RandomStringGenerator;

import java.util.Objects;

public class Booking implements Unit{
    private final String bookingId;
    private final String flightId;
    private final String name;
    private final String surname;
    private final String email;
    private final int bookedTicketsNum;

    public Booking(String flightId, String name, String surname, String email, int bookedTicketsNum){
        int IDLENGTH = 8;
        this.bookingId = RandomStringGenerator.generateRandomString(IDLENGTH);
        this.flightId = flightId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bookedTicketsNum = bookedTicketsNum;
    }
    public Booking(String bookingId,String flightId, String name, String surname, String email, int bookedTicketsNum){
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bookedTicketsNum = bookedTicketsNum;
    }

    public String getBookingId(){ return bookingId; }
    public String getFlightId() {
        return flightId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getBookedTicketsNum() {
        return bookedTicketsNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookedTicketsNum == booking.bookedTicketsNum && Objects.equals(flightId, booking.flightId) && Objects.equals(name, booking.name) && Objects.equals(surname, booking.surname) && Objects.equals(email, booking.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, name, surname, email, bookedTicketsNum);
    }

    @Override
    public String toString(){
        return bookingId + " " + "Flight: " + flightId + " User: " + email;
    }
}
