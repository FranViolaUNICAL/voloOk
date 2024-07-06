package org.clientSide;

import org.serverSide.components.units.User;
import user.UserServiceGrpc;
import user.UserServices;

import java.util.Scanner;

public class UserAccessManager {
    public static User login(UserServiceGrpc.UserServiceBlockingStub blockingStub, String email, String password) {
        UserServices.LoginUserRequest request = ProtoRequestFactory.createLoginRequest(email, password);
        UserServices.LoginUserResponse response = blockingStub.loginUser(request);
        if(response.getSuccess()){
            String name = response.getUser().getName();
            User user = getUser(response, name);
            return user;
        }else{
            return null;
        }
    }

    private static User getUser(UserServices.LoginUserResponse response, String name) {
        String surname = response.getUser().getSurname();
        String emailUser = response.getUser().getEmail();
        String luogoDiNascita = response.getUser().getLuogoDiNascita();
        String regioneDiNascita = response.getUser().getRegioneDiNascita();
        String userId = response.getUser().getUserId();
        String dataDiNascita = response.getUser().getDataDiNascita();
        String passwordUser = response.getUser().getPassword();
        String lastPurchaseDate = response.getUser().getLastPurchaseDate();
        User user = new User(name, surname, emailUser, passwordUser, luogoDiNascita, regioneDiNascita, dataDiNascita,userId,lastPurchaseDate);
        return user;
    }

    public static boolean register(UserServiceGrpc.UserServiceBlockingStub blockingStub, String email, String name, String surname, String luogoDiNascita, String RegioneDiNascita, String dataDiNascita, String password){
        UserServices.RegisterUserRequest request = ProtoRequestFactory.createRegisterRequest(email,password,luogoDiNascita,RegioneDiNascita,dataDiNascita, name, surname);
        UserServices.RegisterUserResponse response = blockingStub.registerUser(request);
        return response.getSuccess();
    }
}
