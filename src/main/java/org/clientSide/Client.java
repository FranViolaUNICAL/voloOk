package org.clientSide;

import io.grpc.*;
import org.clientSide.clientGUI.ClientGUI;
import user.UserServiceGrpc;
import user.UserServices;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private final UserServiceGrpc.UserServiceBlockingStub blockingStub;
    public Map<String, Double> promos = new HashMap<>();
    public boolean isLoggedIn = false;
    private String email;
    private int fidelityPoints = 0;

    private String password;

    ClientGUI gui;

    public Client(Channel channel){
        this.blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public boolean login(String email, String password){
        UserServices.LoginUserResponse response = UserAccessManager.login(blockingStub,email,password);
        boolean check = response.getSuccess();
        if(check){
            this.email = email;
            this.password = password;
            isLoggedIn = true;
            fidelityPoints = response.getUser().getFidelityPoints();
        }
        return check;
    }

    public int getFidelityPoints(){
        return fidelityPoints;
    }

    public void logout(){
        this.email = "";
        isLoggedIn = false;
    }

    public String getEmail(){
        return email;
    }

    public boolean register(String email, String name, String surname, String luogoDiNascita, String RegioneDiNascita, String dataDiNascita, String password) {
        try {
            return UserAccessManager.register(blockingStub, email, name, surname, luogoDiNascita, RegioneDiNascita, dataDiNascita, password);
        }catch(StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return false;
    }

    public boolean deductFidelityPoints(int pointsToDeduct){
        System.out.println(pointsToDeduct);
        try {
            fidelityPoints = UserAccessManager.login(this.blockingStub, this.email, this.password).getUser().getFidelityPoints();
            return blockingStub.deductFidelityPoints(ProtoRequestFactory.deductFidelityPoints(pointsToDeduct, email)).getSuccess();
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return false;
    }

    public void sendNotifications(boolean isRegistered){
        UserServices.NotifyClientRequest request = ProtoRequestFactory.sendNotifications(isRegistered);
        try {
            UserServices.NotifyClientResponse response = blockingStub.notifyClient(request);
            for (UserServices.Promo p : response.getPromoList()) {
                promos.put(p.getDescription(), p.getDiscountFactor());
            }
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
    }

    public UserServices.CancelBookFlightResponse CancelFlight(String flightId, String email){
        try{
            return blockingStub.cancelBookFlight(ProtoRequestFactory.cancelBookFlight(flightId,email));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public UserServices.FlightBookResponse bookFlight(String name, String surname, String flightId, String email, int n){
        try {
            return blockingStub.bookFlight(ProtoRequestFactory.bookFlight(name, surname, flightId, email, n));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public UserServices.PurchaseTicketResponse purchaseTicket(String flightId, String name, String surname, String email, String cardN){
        try {
            if (isLoggedIn) {
                fidelityPoints = UserAccessManager.login(this.blockingStub, this.email, this.password).getUser().getFidelityPoints();
            }
            return blockingStub.purchaseTicket(ProtoRequestFactory.purchaseTicket(flightId, name, surname, email, cardN));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public int fetchFlightPrice(String flightID){
        try {
            return blockingStub.searchFlight(ProtoRequestFactory.searchFlight(flightID)).getPrice();
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return 0;
    }

    public double fetchDiscountFactor(String promoCode, String flightId){
        try {
            String country = blockingStub.searchFlight(ProtoRequestFactory.searchFlight(flightId)).getDestinationCountry();
            return blockingStub.promoCheck(ProtoRequestFactory.checkPromo(promoCode, country, flightId)).getDiscountFactor();
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return 0;
    }

    public UserServices.CheckFlightAvailabilityResponse checkFlightAvailability(String origin, String destination, String date){
        try {
            return blockingStub.checkFlightAvailability(ProtoRequestFactory.checkFlightAvailability(origin, destination, date));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public UserServices.ChangeFlightDateResponse changeFlightDate(String bookingId, String newDepartureDate){
        try {
            return blockingStub.changeFlightDate(ProtoRequestFactory.changeFlightDate(bookingId, newDepartureDate));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public UserServices.ActuallyChangeFlightDateResponse chooseFlightToChangeDate(String bookingId, String newFlightId, int cost, String cardN){
        try {
            return blockingStub.actuallyChangeFlightDate(ProtoRequestFactory.chooseFlightToChangeDate(bookingId, newFlightId, cost, cardN));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public UserServices.FetchAllBookingsResponse fetchMyBookings(String email){
        try {
            return blockingStub.fetchAllBookings(ProtoRequestFactory.fetchMyBookings(email));
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public UserServices.FetchAllTicketsResponse fetchMyTickets(String email){
        try {
            return blockingStub.fetchAllTickets(ProtoRequestFactory.fetchMyTickets(email));
        }catch (RuntimeException e){
            gui.connectionErrorMode();
        }
        return null;
    }

    public boolean cancelBooking(String email, String flightId){
        try{
            return blockingStub.cancelBookFlight(ProtoRequestFactory.cancelBookFlight(flightId,email)).getSuccess();
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
        return false;
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
        ClientGUI gui = new ClientGUI(c);
        c.gui = gui;
        try{
            c.sendNotifications(c.isLoggedIn());
        }catch (StatusRuntimeException e){
            gui.connectionErrorMode();
        }
    }


}
