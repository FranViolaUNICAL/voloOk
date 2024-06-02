package org.components.units;

import org.components.utils.RandomStringGenerator;

import java.util.Objects;

public class Booking implements Unit{
    private String bookingId;
    private final int IDLENGTH = 8;
    private String flightId;
    private String name;
    private String surname;
    private String email;
    private int bookedTicketsNum;

    public Booking(String flightId, String name, String surname, String email, int bookedTicketsNum){
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
}
