package org.clientSide;

import io.grpc.StatusRuntimeException;
import user.UserServiceGrpc;
import user.UserServices;

import java.util.Scanner;

public class UserAccessManager {
    public static UserServices.LoginUserResponse login(UserServiceGrpc.UserServiceBlockingStub blockingStub, String email, String password) throws StatusRuntimeException {
        UserServices.LoginUserRequest request = ProtoRequestFactory.createLoginRequest(email, password);
        UserServices.LoginUserResponse response = blockingStub.loginUser(request);
        return response;
    }

    public static boolean register(UserServiceGrpc.UserServiceBlockingStub blockingStub, String email, String name, String surname, String luogoDiNascita, String RegioneDiNascita, String dataDiNascita, String password) throws StatusRuntimeException{
        UserServices.RegisterUserRequest request = ProtoRequestFactory.createRegisterRequest(email,password,luogoDiNascita,RegioneDiNascita,dataDiNascita, name, surname);
        UserServices.RegisterUserResponse response = blockingStub.registerUser(request);
        return response.getSuccess();
    }
}
