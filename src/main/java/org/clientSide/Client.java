package org.clientSide;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
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
    private Map<String, Double> promos = new HashMap<>();
    private boolean isLoggedIn = false;

    private String email;

    public Client(Channel channel){
        this.blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public User login(String email, String password){
        User u = UserAccessManager.login(blockingStub,email,password);
        if(u != null){
            this.email = email;
        }
        return u;
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

    public UserServices.PurchaseTicketResponse purchaseTicket(String flightId, String name, String surname, String email, String cardN){
        return blockingStub.purchaseTicket(ProtoRequestFactory.purchaseTicket(flightId, name, surname, email, cardN));
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
        User user;
        while(true){
            System.out.println("Select your option");
            System.out.println("1: login");
            System.out.println("2: register");
            System.out.println("3: check Promos");
            System.out.println("0: exit");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());
            switch(choice){
                case 1:
                    System.out.println("Enter email:");
                    String email = sc.nextLine();
                    System.out.println("Enter password");
                    String password = sc.nextLine();
                    user = c.login(email,password);
                    if(user == null){
                        System.out.println("Login unsuccesfull. Check credentials.");
                    }else{
                        c.setLogin();
                        System.out.println("Logged in as user: " + user.getUserId());
                        new promoThread(c).start();
                    }
                    break;
                case 2:
                    System.out.println("Enter email:");
                    String newEmail = sc.nextLine();
                    System.out.println("Enter password:");
                    String newPassword = sc.nextLine();
                    System.out.println("Enter your name:");
                    String name = sc.nextLine();
                    System.out.println("Enter your surname:");
                    String surname = sc.nextLine();
                    System.out.println("Enter your city of birth:");
                    String luogoDiNascita = sc.nextLine();
                    System.out.println("Enter your region of birth:");
                    String regioneDiNascita = sc.nextLine();
                    System.out.println("Enter your date of birth:");
                    String dataDiNascita = sc.nextLine();
                    if(c.register(newEmail,name,surname,luogoDiNascita,regioneDiNascita,dataDiNascita,newPassword)){
                        System.out.println("Registered succesfully");
                    }
                    else{
                        System.out.println("Did not register. Try again");
                    }
                    break;
                case 3:
                    for(String key : c.promos.keySet()){
                        System.out.println(key);
                    }
                    break;
                case 0:
                    System.out.println("Shutting down...");
                    exit(0);
            }
        }
    }


}
