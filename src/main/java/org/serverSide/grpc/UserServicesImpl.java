package org.serverSide.grpc;

import io.grpc.Status;
import org.serverSide.components.factories.UserServicesFactory;
import org.serverSide.components.observers.JsonManagerObs;
import org.serverSide.components.singletonLists.BookingList;
import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.units.Flight;
import org.serverSide.components.units.Booking;
import org.serverSide.components.units.Ticket;
import org.serverSide.components.singletonLists.TicketList;
import org.serverSide.components.units.User;
import org.serverSide.components.singletonLists.UserList;
import user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import user.UserServices;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class UserServicesImpl extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void registerUser(UserServices.RegisterUserRequest request, StreamObserver<UserServices.RegisterUserResponse> responseObserver) {
        new registerThread(this,request,responseObserver).start();
    }

    public void regUserThread(UserServices.RegisterUserRequest request, StreamObserver<UserServices.RegisterUserResponse> responseObserver){
        try{
            boolean userNotFoundInJson = JsonManagerObs.checkJsonForEmail(request.getEmail());
            boolean success = false;
            String message = "User already registered.";
            String userId = "";
            // SE L'UTENTE NON RISULTA REGISTRATO PROCEDO A INSERIRLO NEL JSON
            if(userNotFoundInJson){
                User newUser = new User(request.getName(), request.getSurname(), request.getEmail(), request.getPassword(), request.getLuogoDiNascita(), request.getRegioneDiNascita(), request.getDataDiNascita());
                UserList userlist = UserList.getInstance();
                userlist.add(newUser);
                success = true;
                message = String.format("User with email %s successfully registered", request.getEmail());
                userId = newUser.getUserId();
            }
            //CREO LA RISPOSTA
            UserServices.RegisterUserResponse response = UserServicesFactory.createRegisterUserResponse(message,success);
            //INVIO LA RISPOSTA
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (IOException e){
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }
    @Override
    public void loginUser(UserServices.LoginUserRequest request, StreamObserver<UserServices.LoginUserResponse> responseObserver) {
        ThreadPoolManager.getExecutorService().execute(() -> {
            try {
                boolean userFoundInJson = JsonManagerObs.checkJsonForEmail(request.getEmail());
                System.out.println(userFoundInJson);
                boolean success = false;
                String message;
                if (!userFoundInJson) {
                    message = "User does not exist.";
                }
                User user = JsonManagerObs.checkCredentials(request.getEmail(), request.getPassword());
                boolean checkCredentials = !(user == null);
                if (!checkCredentials) {
                    message = "Please check credentials.";
                } else {
                    message = "Login successful!";
                    success = true;
                }
                UserServices.LoginUserResponse response = UserServicesFactory.createLoginUserResponse(message, success, user);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (IOException e) {
                responseObserver.onError(Status.INTERNAL.asRuntimeException());
            }
        });
    }

    @Override
    public void purchaseTicket(UserServices.PurchaseTicketRequest request, StreamObserver<UserServices.PurchaseTicketResponse> responseObserver) {
        ThreadPoolManager.getExecutorService().execute(() -> {
            try {
                boolean success = JsonManagerObs.checkForTicketPurchase(request.getFlightId(), request.getCardNumber(), request.getUserEmail());
                String message = success ? "Purchase successful" : "Purchase failed";
                if (success) {
                    TicketList ticketList = TicketList.getInstance();
                    Ticket newTicket = new Ticket(request.getFlightId(), request.getName(), request.getSurname(), request.getUserEmail());
                    ticketList.add(newTicket);
                    FlightList.getInstance().occupySeat(request.getFlightId());
                    JsonManagerObs.checkForFidelity(request.getUserEmail(), request.getFlightId());
                }
                UserServices.PurchaseTicketResponse response = UserServicesFactory.createPurchaseTicketResponse(message, success);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (IOException e) {
                responseObserver.onError(Status.INTERNAL.asRuntimeException());
            }
        });
    }

    @Override
    public void bookFlight(UserServices.FlightBookRequest request, StreamObserver<UserServices.FlightBookResponse> responseObserver){
        ThreadPoolManager.getExecutorService().execute(() -> {
            try {
                boolean success = JsonManagerObs.checkForBooking(request.getFlightId(), request.getBookedTicketsNum(), request.getEmail());
                String message = success ? "Booking successful" : "Booking failed";
                if (success) {
                    Booking booking = new Booking(request.getFlightId(), request.getName(), request.getSurname(), request.getEmail(), request.getBookedTicketsNum());
                    for (int i = 0; i < booking.getBookedTicketsNum(); i++) {
                        FlightList.getInstance().occupySeat(request.getFlightId());
                    }
                    BookingList.getInstance().add(booking);
                }
                UserServices.FlightBookResponse response = UserServicesFactory.createFlightBookResponse(message, success);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void checkFlightAvailability(UserServices.CheckFlightAvailabilityRequest request, StreamObserver<UserServices.CheckFlightAvailabilityResponse> responseObserver) {
        ThreadPoolManager.getExecutorService().execute(() -> {
            try {
                List<Flight> l = FlightList.getInstance().checkAvailabilityFromDate(request.getOrigin(), request.getDestination(), request.getDate());
                UserServices.CheckFlightAvailabilityResponse response = UserServicesFactory.createFlightAvailabilityResponse(l);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (ParseException e) {
                responseObserver.onError(Status.INTERNAL.asRuntimeException());
            }
        });
    }

    @Override
    public void changeFlightDate(UserServices.ChangeFlightDateRequest request, StreamObserver<UserServices.ChangeFlightDateResponse> responseObserver) {
        ThreadPoolManager.getExecutorService().execute(() -> {
            try {
                List<Flight> list = JsonManagerObs.isDateChangePossible(request.getBookingId(), request.getNewDateDeparture());
                boolean possible = !list.isEmpty();
                String message = possible ? "Date change is possible. Consult list for options" : "Flight change impossible";
                UserServices.ChangeFlightDateResponse response = UserServicesFactory.createChangeFlightDateResponse(message, possible, list);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (ParseException e) {
                responseObserver.onError(Status.INTERNAL.asRuntimeException());
            }
        });
    }

    public void actuallyChangeFlightDate(UserServices.ActuallyChangeFlightDateRequest request, StreamObserver<UserServices.ActuallyChangeFlightDateResponse> responseObserver){
        ThreadPoolManager.getExecutorService().execute(() -> {
            String newBookingId = JsonManagerObs.changeBookingDate(request.getBookingId(), request.getNewFlightId());
            boolean success = !(newBookingId == null);
            String message = success ? "Date changed successfully" : "Oops! Something went wrong.";
            UserServices.ActuallyChangeFlightDateResponse response = UserServicesFactory.createActuallyChangeFlightDateResponse(message, success, newBookingId);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }

    public void cancelBookFlight(UserServices.CancelBookFlightRequest request, StreamObserver<UserServices.CancelBookFlightResponse> responseObserver){
        ThreadPoolManager.getExecutorService().execute(() -> {
            boolean success = JsonManagerObs.removeFlightBooking(request.getFlightId(), request.getEmail());
            String message = success ? "Booking removed succesfully" : "Could not remove booking.";
            UserServices.CancelBookFlightResponse response = UserServicesFactory.createCancelBookFlightResponse(message, success);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }

    public void notifyClient(UserServices.NotifyClientRequest request, StreamObserver<UserServices.NotifyClientResponse> responseObserver){
        ThreadPoolManager.getExecutorService().execute(()-> {
            UserServices.NotifyClientResponse response = UserServicesFactory.createNotifyClientResponse(request.getIsRegistered());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        });
    }


}
