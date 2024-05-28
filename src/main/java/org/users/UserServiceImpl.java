package org.users;

import com.fasterxml.jackson.core.type.TypeReference;
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
            ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
            boolean userNotFoundInJson = checkJsonForEmail(request.getEmail());
            boolean success = false;
            String message = "User already registered.";
            // SE L'UTENTE NON RISULTA REGISTRATO PROCEDO A INSERIRLO NEL JSON
            if(userNotFoundInJson){
                User newUser = new User(request.getEmail(), request.getPassword(), request.getLuogoDiNascita(), request.getRegioneDiNascita(), request.getDataDiNascita());
                UserList userlist = UserList.getInstance();
                userlist.add(newUser);
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/userDatabase.json"),userlist);
                success = true;
                message = String.format("User with email %s successfully registered", request.getEmail());
            }

            //CREO LA RISPOSTA
            UserServices.RegisterUserResponse response = UserServices.RegisterUserResponse.newBuilder()
                    .setSuccess(success)
                    .setMessage(message)
                    .build();
            //INVIO LA RISPOSTA
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (IOException e){
            e.printStackTrace();
            responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
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
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();
        //TRASFORMO QUELLO CHE IL MAPPER LEGGE IN UNA MAPPA
        Map<String,Object> map = mapper.readValue(new File("src/userDatabase.json"),new TypeReference<Map<String,Object>>(){});
        //OTTENGO UNA LISTA DAL VALORE DELLA MAPPA CON KEY USERLIST
        List mainMap2 = (List) map.get("userList");

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
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getObjectMapper();

        //TRASFORMO QUELLO CHE IL MAPPER LEGGE IN UNA MAPPA
        Map<String,Object> map = mapper.readValue(new File("src/userDatabase.json"),new TypeReference<Map<String,Object>>(){});

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
