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
            //PRENDO MAPPER PER RICERCA SU FILE JSON
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();

            //TRASFORMO QUELLO CHE IL MAPPER LEGGE IN UNA MAPPA
            Map<String,Object> map = mapper.readValue(ObjectMapperSingleton.JSON,new TypeReference<Map<String,Object>>(){});

            //OTTENGO UNA LISTA DAL VALORE DELLA MAPPA CON KEY USERLIST
            List mainMap2 = (List) map.get("userlist");

            //GIRO LA LISTA PER CONTROLLARE SE L'UTENTE E' GIA' REGISTRATO
            boolean success = true;
            for(int i = 0; i < mainMap2.size(); i++){
                String email = (String)((Map)mainMap2.get(i)).get("email");
                if(email.equals(request.getEmail())){
                    success = false;
                }
            }

            // SE L'UTENTE NON RISULTA REGISTRATO PROCEDO A INSERIRLO NEL JSON
            if(success){
                User newUser = new User(request.getEmail(), request.getPassword(), false);
                UserList userlist = UserList.getInstance();
                UserList.add(newUser);
                mapper.writeValue(ObjectMapperSingleton.JSON,userlist);
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
            responseObserver.onError(Status.ABORTED.asRuntimeException());
        }
    }
}
