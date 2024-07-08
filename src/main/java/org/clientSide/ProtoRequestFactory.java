package org.clientSide;

import user.UserServices;

public class ProtoRequestFactory {
    public static UserServices.LoginUserRequest createLoginRequest(String email, String password){
        return UserServices.LoginUserRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();
    }

    public static UserServices.RegisterUserRequest createRegisterRequest(String email, String password, String luogoDiNascita, String regioneDiNascita, String dataDiNascita, String name, String surname){
        return UserServices.RegisterUserRequest.newBuilder()
                .setName(name)
                .setSurname(surname)
                .setDataDiNascita(dataDiNascita)
                .setRegioneDiNascita(regioneDiNascita)
                .setLuogoDiNascita(luogoDiNascita)
                .setPassword(password)
                .setEmail(email)
                .build();
    }

    public static UserServices.NotifyClientRequest sendNotifications(boolean isRegistered){
        return UserServices.NotifyClientRequest.newBuilder()
                .setIsRegistered(isRegistered)
                .build();
    }

    public static UserServices.CancelBookFlightRequest cancelBookFlight(String flightId, String email){
        return UserServices.CancelBookFlightRequest.newBuilder()
                .setEmail(email)
                .setFlightId(flightId)
                .build();
    }

    public static UserServices.FlightBookRequest bookFlight(String name, String surname, String flightId, String email, int n){
        return UserServices.FlightBookRequest.newBuilder()
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .setFlightId(flightId)
                .setBookedTicketsNum(n)
                .build();
    }

    public static UserServices.PurchaseTicketRequest purchaseTicket(String flightId, String name, String surname, String email, String cardN){
        return UserServices.PurchaseTicketRequest.newBuilder()
                .setName(name)
                .setSurname(surname)
                .setUserEmail(email)
                .setFlightId(flightId)
                .setCardNumber(cardN)
                .build();
    }

    public static UserServices.CheckFlightAvailabilityRequest checkFlightAvailability(String origin, String destination, String date){
        return UserServices.CheckFlightAvailabilityRequest.newBuilder()
                .setOrigin(origin)
                .setDestination(destination)
                .setDate(date)
                .build();
    }

    public static UserServices.ChangeFlightDateRequest changeFlightDate(String bookingId, String newDepartureDate){
        return UserServices.ChangeFlightDateRequest.newBuilder()
                .setBookingId(bookingId)
                .setNewDateDeparture(newDepartureDate)
                .build();
    }

    public static UserServices.ActuallyChangeFlightDateRequest chooseFlightToChangeDate(String bookingId, String newFlightId, int cost, String cardN){
        return UserServices.ActuallyChangeFlightDateRequest.newBuilder()
                .setBookingId(bookingId)
                .setNewFlightId(newFlightId)
                .setCost(cost)
                .setCardNumber(cardN)
                .build();
    }

    public static UserServices.FetchAllBookingsRequest fetchMyBookings(String email){
        return UserServices.FetchAllBookingsRequest.newBuilder()
                .setEmail(email)
                .build();
    }

    public static UserServices.FetchAllTicketsRequest fetchMyTickets(String email){
        return UserServices.FetchAllTicketsRequest.newBuilder()
                .setEmail(email)
                .build();
    }

    public static UserServices.PromoCheckRequest checkPromo(String promoCode, String country, String flightId){
        return UserServices.PromoCheckRequest.newBuilder()
                .setPromoCode(promoCode)
                .setCountryCode(country)
                .setFlightId(flightId)
                .build();
    }

    public static UserServices.SearchFlightRequest searchFlight(String flightId){
        return UserServices.SearchFlightRequest.newBuilder()
                .setFlightId(flightId)
                .build();
    }


}
