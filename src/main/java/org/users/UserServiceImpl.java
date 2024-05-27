package org.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Status;
import org.components.ObjectMapperSingleton;
import user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import user.UserServices;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void registerUser(UserServices.RegisterUserRequest request, StreamObserver<UserServices.RegisterUserResponse> responseObserver) {
        try{
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            boolean userFoundInJson = checkJsonForEmail(request.getEmail());
            boolean success = false;
            // SE L'UTENTE NON RISULTA REGISTRATO PROCEDO A INSERIRLO NEL JSON
            if(!userFoundInJson){
                User newUser = new User(request.getEmail(), request.getPassword(), request.getLuogoDiNascita(), request.getRegioneDiNascita(), request.getDataDiNascita());
                UserList userlist = UserList.getInstance();
                UserList.add(newUser);
                mapper.writeValue(ObjectMapperSingleton.JSON,userlist);
                success = true;
            }
            //CREO LA RISPOSTA
            String message = success ? "User registered successfully" : "User registration failed";
            UserServices.RegisterUserResponse response = UserServices.RegisterUserResponse.newBuilder()
                    .setSuccess(success)
                    .setMessage(message)
                    .build();
            //INVIO LA RISPOSTA
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (IOException e){
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void loginUser(UserServices.LoginUserRequest request, StreamObserver<UserServices.LoginUserResponse> responseObserver) {
        try{
            boolean userFoundInJson = checkJsonForEmail(request.getEmail());
            boolean success = false;
            String message;
            if(!userFoundInJson){
                message = "User does not exist.";
            }
            boolean checkCredentials = checkCredentials(request.getEmail(), request.getPassword());
            if(!checkCredentials){
                message = "Please check credentials.";
            }
            else{
                message = "Login successful!";
                success = true;
            }
            UserServices.LoginUserResponse response = UserServices.LoginUserResponse.newBuilder()
                    .setMessage(message)
                    .setSuccess(success)
                    .build();
        }catch (IOException e){
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    private boolean checkJsonForEmail(String email) throws IOException {
        //PRENDO MAPPER PER RICERCA SU FILE JSON
        ObjectMapper mapper = ObjectMapperSingleton.getInstance();

        //TRASFORMO QUELLO CHE IL MAPPER LEGGE IN UNA MAPPA
        Map<String,Object> map = mapper.readValue(ObjectMapperSingleton.JSON,new TypeReference<Map<String,Object>>(){});

        //OTTENGO UNA LISTA DAL VALORE DELLA MAPPA CON KEY USERLIST
        List mainMap2 = (List) map.get("userlist");

        //GIRO LA LISTA PER CONTROLLARE SE L'UTENTE E' GIA' REGISTRATO
        boolean success = true;
        for(int i = 0; i < mainMap2.size(); i++){
            String s = (String)((Map)mainMap2.get(i)).get("email");
            if(s.equals(email)){
                success = false;
            }
        }
        return success;
    }

    private boolean checkCredentials(String email, String password) throws IOException {
        //PRENDO MAPPER PER RICERCA SU FILE JSON
        ObjectMapper mapper = ObjectMapperSingleton.getInstance();

        //TRASFORMO QUELLO CHE IL MAPPER LEGGE IN UNA MAPPA
        Map<String,Object> map = mapper.readValue(ObjectMapperSingleton.JSON,new TypeReference<Map<String,Object>>(){});

        //OTTENGO UNA LISTA DAL VALORE DELLA MAPPA CON KEY USERLIST
        List mainMap2 = (List) map.get("userlist");

        //GIRO LA LISTA PER CONTROLLARE SE L'UTENTE E' GIA' REGISTRATO
        boolean success = false;
        for(int i = 0; i < mainMap2.size(); i++){
            String s = (String)((Map)mainMap2.get(i)).get("email");
            String s2 = (String)((Map)mainMap2.get(i)).get("password");
            if(s.equals(email) && s2.equals(password)){
                success = true;
            }
        }
        return success;
    }
}
