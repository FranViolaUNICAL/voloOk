package org.serverSide.components.factories;

import org.serverSide.components.singletonLists.PromoList;
import org.serverSide.components.units.Flight;
import org.serverSide.components.units.Promo;
import org.serverSide.components.units.Unit;
import org.serverSide.components.units.User;
import user.UserServices;

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



}
