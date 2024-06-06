package org.serverSide.components.factories;

import org.serverSide.components.units.Flight;
import user.UserServices;

import java.util.ArrayList;
import java.util.List;

public class UserServicesFactory {
    public static UserServices.RegisterUserResponse createRegisterUserResponse(String message, boolean success, String userId) {
        return UserServices.RegisterUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .setUserId(userId)
                .build();
    }
    public static UserServices.LoginUserResponse createLoginUserResponse(String message, boolean success) {
        return UserServices.LoginUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
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


}
