package org.clientSide;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import org.clientSide.clientGUI.ClientGUI;
import org.serverSide.components.units.User;
import user.UserServiceGrpc;
import user.UserServices;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Client {
    private final UserServiceGrpc.UserServiceBlockingStub blockingStub;
    public Map<String, Double> promos = new HashMap<>();
    public boolean isLoggedIn = false;

    private String email;

    public Client(Channel channel){
        this.blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public User login(String email, String password){
        User u = UserAccessManager.login(blockingStub,email,password);
        if(u != null){
            this.email = email;
            isLoggedIn = true;
        }
        return u;
    }

    public void logout(){
        this.email = "";
        isLoggedIn = false;
    }

    public String getEmail(){
        return email;
    }

    public boolean register(String email, String name, String surname, String luogoDiNascita, String RegioneDiNascita, String dataDiNascita, String password) {
        return UserAccessManager.register(blockingStub, email, name, surname, luogoDiNascita, RegioneDiNascita, dataDiNascita, password);
    }

    public void sendNotifications(boolean isRegistered){
        UserServices.NotifyClientRequest request = ProtoRequestFactory.sendNotifications(isRegistered);
        UserServices.NotifyClientResponse response = blockingStub.notifyClient(request);
        for(UserServices.Promo p : response.getPromoList()){
            promos.put(p.getDescription(),p.getDiscountFactor());
        }
    }

    public UserServices.CancelBookFlightResponse CancelFlight(String flightId, String email){
        return blockingStub.cancelBookFlight(ProtoRequestFactory.cancelBookFlight(flightId,email));
    }

    public UserServices.FlightBookResponse bookFlight(String name, String surname, String flightId, String email, int n){
        return blockingStub.bookFlight(ProtoRequestFactory.bookFlight(name,surname,flightId,email,n));
    }

    public UserServices.PurchaseTicketResponse purchaseTicket(String flightId, String name, String surname, String email, String cardN,String promoCode){
        String country = blockingStub.searchFlight(ProtoRequestFactory.searchFlight(flightId)).getDestinationCountry();
        if(blockingStub.promoCheck(ProtoRequestFactory.checkPromo(promoCode, country, flightId)).getSuccess()){
            return blockingStub.purchaseTicket(ProtoRequestFactory.purchaseTicket(flightId, name, surname, email, cardN));
        }else{
            return null;
        }
    }

    public int fetchFlightPrice(String flightID){
        return blockingStub.searchFlight(ProtoRequestFactory.searchFlight(flightID)).getPrice();
    }

    public double fetchDiscountFactor(String promoCode, String flightId){
        String country = blockingStub.searchFlight(ProtoRequestFactory.searchFlight(flightId)).getDestinationCountry();
        return blockingStub.promoCheck(ProtoRequestFactory.checkPromo(promoCode,country,flightId)).getDiscountFactor();
    }

    public UserServices.CheckFlightAvailabilityResponse checkFlightAvailability(String origin, String destination, String date){
        return blockingStub.checkFlightAvailability(ProtoRequestFactory.checkFlightAvailability(origin,destination,date));
    }

    public UserServices.ChangeFlightDateResponse changeFlightDate(String bookingId, String newDepartureDate){
        return blockingStub.changeFlightDate(ProtoRequestFactory.changeFlightDate(bookingId,newDepartureDate));
    }

    public UserServices.ActuallyChangeFlightDateResponse chooseFlightToChangeDate(String bookingId, String newFlightId, int cost, String cardN){
        return blockingStub.actuallyChangeFlightDate(ProtoRequestFactory.chooseFlightToChangeDate(bookingId,newFlightId,cost,cardN));
    }

    public UserServices.FetchAllBookingsResponse fetchMyBookings(String email){
        return blockingStub.fetchAllBookings(ProtoRequestFactory.fetchMyBookings(email));
    }

    public UserServices.FetchAllTicketsResponse fetchMyTickets(String email){
        return blockingStub.fetchAllTickets(ProtoRequestFactory.fetchMyTickets(email));
    }

    boolean isLoggedIn(){
        return isLoggedIn;
    }

    private void setLogin(){
        isLoggedIn = true;
    }

    public static void main(String[] args){
        String target = "localhost:50051";
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        Client c = new Client(channel);
        new promoThread(c).start();
        new ClientGUI(c);
    }


}
