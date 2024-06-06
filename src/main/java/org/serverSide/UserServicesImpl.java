package org.serverSide;

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

public class UserServicesImpl extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void registerUser(UserServices.RegisterUserRequest request, StreamObserver<UserServices.RegisterUserResponse> responseObserver) {
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
            UserServices.RegisterUserResponse response = UserServicesFactory.createRegisterUserResponse(message,success,userId);
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
        try{
            boolean userFoundInJson = JsonManagerObs.checkJsonForEmail(request.getEmail());
            boolean success = false;
            String message;
            if(!userFoundInJson){
                message = "User does not exist.";
            }
            boolean checkCredentials = JsonManagerObs.checkCredentials(request.getEmail(), request.getPassword());
            if(!checkCredentials){
                message = "Please check credentials.";
            }
            else{
                message = "Login successful!";
                success = true;
            }
            UserServices.LoginUserResponse response = UserServicesFactory.createLoginUserResponse(message,success);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (IOException e){
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void purchaseTicket(UserServices.PurchaseTicketRequest request, StreamObserver<UserServices.PurchaseTicketResponse> responseObserver) {
        try{
            boolean success = JsonManagerObs.checkForTicketPurchase(request.getFlightId());
            String message = success ? "Purchase successful" : "Purchase failed";
            if(success){
                TicketList ticketList = TicketList.getInstance();
                Ticket newTicket = new Ticket(request.getFlightId(), request.getName(), request.getSurname(), request.getUserEmail());
                ticketList.add(newTicket);
                FlightList.getInstance().occupySeat(request.getFlightId());
                JsonManagerObs.checkForFidelity(request.getUserEmail(), request.getFlightId());
            }
            UserServices.PurchaseTicketResponse response = UserServicesFactory.createPurchaseTicketResponse(message,success);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch(IOException e){
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void bookFlight(UserServices.FlightBookRequest request, StreamObserver<UserServices.FlightBookResponse> responseObserver){
        try{
            boolean success = JsonManagerObs.checkForBooking(request.getFlightId(), request.getBookedTicketsNum(), request.getEmail());
            String message = success ? "Booking successful" : "Booking failed";
            if(success){
                Booking booking = new Booking(request.getFlightId(), request.getName(), request.getSurname(), request.getEmail(), request.getBookedTicketsNum());
                for(int i = 0; i < booking.getBookedTicketsNum(); i++){
                    FlightList.getInstance().occupySeat(request.getFlightId());
                }
                BookingList.getInstance().add(booking);
            }
            UserServices.FlightBookResponse response = UserServicesFactory.createFlightBookResponse(message,success);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public void checkFlightAvailability(UserServices.CheckFlightAvailabilityRequest request, StreamObserver<UserServices.CheckFlightAvailabilityResponse> responseObserver) {
        try{
            List<Flight> l = FlightList.getInstance().checkAvailabilityFromDate(request.getOrigin(), request.getDestination(), request.getDate());
            UserServices.CheckFlightAvailabilityResponse response =  UserServicesFactory.createFlightAvailabilityResponse(l);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (ParseException e){
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void changeFlightDate(UserServices.ChangeFlightDateRequest request, StreamObserver<UserServices.ChangeFlightDateResponse> responseObserver) {
        try{
            List<Flight> list = JsonManagerObs.isDateChangePossible(request.getBookingId(), request.getNewDateDeparture());
            boolean possible = !list.isEmpty();
            String message =  possible ? "Date change is possible. Consult list for options" : "Flight change impossible";
            UserServices.ChangeFlightDateResponse response = UserServicesFactory.createChangeFlightDateResponse(message, possible, list);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (ParseException e){
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    public void actuallyChangeFlightDate(UserServices.ActuallyChangeFlightDateRequest request, StreamObserver<UserServices.ActuallyChangeFlightDateResponse> responseObserver){
        String newBookingId = JsonManagerObs.changeBookingDate(request.getBookingId(), request.getNewFlightId());
        boolean success = !(newBookingId == null);
        String message = success ? "Date changed successfully" : "Oops! Something went wrong.";
        UserServices.ActuallyChangeFlightDateResponse response = UserServicesFactory.createActuallyChangeFlightDateResponse(message, success, newBookingId);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


}
