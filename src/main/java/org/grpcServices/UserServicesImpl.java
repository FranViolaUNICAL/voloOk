package org.grpcServices;

import io.grpc.Status;
import org.components.factories.UserServicesFactory;
import org.components.observers.JsonManagerObs;
import org.components.singletonLists.FlightList;
import org.components.units.Ticket;
import org.components.singletonLists.TicketList;
import org.components.units.User;
import org.components.singletonLists.UserList;
import user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import user.UserServices;

import java.io.IOException;

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


}
