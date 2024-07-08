package org.serverSide.components.factories;

import org.serverSide.components.singletonLists.BookingList;
import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.singletonLists.PromoList;
import org.serverSide.components.singletonLists.TicketList;
import org.serverSide.components.units.*;
import org.serverSide.grpc.PromoManager;
import user.UserServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServicesFactory {
    public static UserServices.RegisterUserResponse createRegisterUserResponse(String message, boolean success) {
        return UserServices.RegisterUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();
    }
    public static UserServices.LoginUserResponse createLoginUserResponse(String message, boolean success, User user) {

        return user != null ? UserServices.LoginUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .setUser(createUser(user))
                .build() :
                UserServices.LoginUserResponse.newBuilder()
                        .setSuccess(success)
                        .setMessage(message)
                        .setUser(createUser(new User("null","null","null","null","null","null","null")))
                        .build();
    }

    private static UserServices.User createUser(User user){
        return UserServices.User.newBuilder()
                .setUserId(user.getUserId())
                .setDataDiNascita(user.getDataDiNascita())
                .setLuogoDiNascita(user.getLuogoDiNascita())
                .setRegioneDiNascita(user.getRegioneDiNascita())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setLastPurchaseDate(user.getLastPurchaseDate())
                .build();
    }
    public static UserServices.PurchaseTicketResponse createPurchaseTicketResponse(String message, boolean success) {
        return UserServices.PurchaseTicketResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();
    }

    public static UserServices.CheckFlightAvailabilityResponse createFlightAvailabilityResponse(List<Flight> list){
        List<UserServices.Flight> listResponse = new ArrayList<>();
        for(Flight f : list){
            listResponse.add(createFlight(f));
        }
        return UserServices.CheckFlightAvailabilityResponse.newBuilder()
                .addAllFlights(listResponse)
                .build();
    }

    public static UserServices.Flight createFlight(Flight f){
        return UserServices.Flight.newBuilder()
                .setFlightId(f.getFlightId())
                .setOrigin(f.getOrigin())
                .setDestination(f.getDestination())
                .setArrivalTime(f.getArrivalTime())
                .setDepartureTime(f.getDepartureTime())
                .setAvailableSeats(f.getAvailableSeats())
                .setPrice(f.getPrice())
                .build();
    }
    public static UserServices.FlightBookResponse createFlightBookResponse(String message, boolean success) {
        return UserServices.FlightBookResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();
    }

    public static UserServices.ChangeFlightDateResponse createChangeFlightDateResponse(String message, boolean possible, List<Flight> list){
        List<UserServices.Flight> listResponse = new ArrayList<>();
        for(Flight f : list){
            UserServices.Flight fR = createFlight(f);
            listResponse.add(fR);
        }
        return UserServices.ChangeFlightDateResponse.newBuilder()
                .addAllFlights(listResponse)
                .setMessage(message)
                .setPossible(possible)
                .build();
    }

    public static UserServices.ActuallyChangeFlightDateResponse createActuallyChangeFlightDateResponse(String message, boolean success, String newBookingId){
        return UserServices.ActuallyChangeFlightDateResponse.newBuilder()
                .setMessage(message)
                .setSuccess(success)
                .setNewBookingId(newBookingId)
                .build();
    }

    public static UserServices.CancelBookFlightResponse createCancelBookFlightResponse(String message, boolean success){
        return UserServices.CancelBookFlightResponse.newBuilder()
                .setMessage(message)
                .setSuccess(success)
                .build();
    }

    public static UserServices.Promo createPromo(Promo p){
        return UserServices.Promo.newBuilder()
                .setDescription(p.getDescription())
                .setCode(p.getCode())
                .setEndDate(p.getEndDate())
                .setOrigin(p.getOrigin())
                .setDestination(p.getDestination())
                .setFidelityOnly(p.getFidelityOnly())
                .setDiscountFactor(p.getDiscountFactor())
                .build();
    }

    public static UserServices.NotifyClientResponse createNotifyClientResponse(boolean isRegistered){
        List<UserServices.Promo> listResponse = new ArrayList<>();
        PromoList pl = PromoList.getInstance();
        for(Unit uP : pl.getPromoList()){
            Promo p = (Promo) uP;
            if(p.getFidelityOnly()){
                if(isRegistered){
                    UserServices.Promo pResponse = createPromo(p);
                    listResponse.add(pResponse);
                }
            }else{
                UserServices.Promo pResponse = createPromo(p);
                listResponse.add(pResponse);
            }
        }
        return UserServices.NotifyClientResponse.newBuilder()
                .addAllPromo(listResponse)
                .build();
    }

    public static UserServices.FetchAllTicketsResponse createFetchAllTicketsResponse(String email){
        List<UserServices.Ticket> lTicket = new ArrayList<>();
        List<Unit> tl = TicketList.getInstance().getTicketList();
        for(Unit uT : tl) {
            Ticket t = (Ticket) uT;
            if (t.getPassengerEmail().equals(email)) {
                UserServices.Ticket tResponse = createTicket(t);
                lTicket.add(tResponse);
            }
        }
        if(!lTicket.isEmpty()) {
            return UserServices.FetchAllTicketsResponse.newBuilder()
                    .setSuccess(true)
                    .addAllTickets(lTicket)
                    .build();
        }
        else{
            return UserServices.FetchAllTicketsResponse.newBuilder()
                    .setSuccess(false)
                    .addAllTickets(lTicket)
                    .build();
        }
    }

    private static UserServices.Ticket createTicket(Ticket t){
        return UserServices.Ticket.newBuilder()
                .setEmail(t.getPassengerEmail())
                .setTicketId(t.getTicketId())
                .setPassengerName(t.getPassengerName())
                .setPassengerSurname(t.getPassengerSurname())
                .setFlightId(t.getFlightId())
                .build();
    }

    public static UserServices.FetchAllBookingsResponse createFetchAllBookingsResponse(String email){
        List<UserServices.Booking> lBooking = new ArrayList<>();
        List<Unit> bl = BookingList.getInstance().getBookingList();
        for(Unit uB : bl) {
            Booking b = (Booking) uB;
            if (b.getEmail().equals(email)) {
                UserServices.Booking tResponse = createBooking(b);
                lBooking.add(tResponse);
            }
        }
        if(!lBooking.isEmpty()) {
            return UserServices.FetchAllBookingsResponse.newBuilder()
                    .setSuccess(true)
                    .addAllBookings(lBooking)
                    .build();
        }
        else{
            return UserServices.FetchAllBookingsResponse.newBuilder()
                    .setSuccess(false)
                    .addAllBookings(lBooking)
                    .build();
        }
    }

    private static UserServices.Booking createBooking(Booking b){
        return UserServices.Booking.newBuilder()
                .setBookingId(b.getBookingId())
                .setEmail(b.getEmail())
                .setName(b.getName())
                .setSurname(b.getSurname())
                .setFlightId(b.getFlightId())
                .setBookedTicketsNum(b.getBookedTicketsNum())
                .build();
    }

    public static UserServices.PromoCheckResponse createPromoCheckResponse(String promoCode, String country, String flightId){
        boolean success = false;
        String aita = null;
        double discountFactor = 0;
        List<Unit> flightList = FlightList.getInstance().getFlightList();
        for(Unit uF: flightList){
            Flight f = (Flight) uF;
            if(f.getFlightId().equals(flightId)){
                aita = f.getDestination();
            }
        }
        List<Unit> promoList = PromoList.getInstance().getPromoList();
        for(Unit uP : promoList){
            Promo p = (Promo) uP;
            if(p.getCode().equals(promoCode) && !(aita == null) && (p.getDestination().equals(country))){
                try {
                     success = PromoManager.isAirportInCountry(aita, country);
                     discountFactor = p.getDiscountFactor();
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
            }
        }
        return UserServices.PromoCheckResponse.newBuilder()
                .setSuccess(success)
                .setDiscountFactor(discountFactor)
                .build();
    }


    public static UserServices.SearchFlightResponse createSearchFlightResponse(String flightId) {
        String destination = null;
        int price = -1;
        List<Unit> flightList = FlightList.getInstance().getFlightList();
        for(Unit uF : flightList){
            Flight f = (Flight) uF;
            if(f.getFlightId().equals(flightId)){
                try {
                    destination = PromoManager.findCountry(f.getDestination());
                    price = f.getPrice();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return UserServices.SearchFlightResponse.newBuilder()
                .setSuccess(!(destination == null))
                .setDestinationCountry(destination)
                .setPrice(price)
                .build();
    }
}
