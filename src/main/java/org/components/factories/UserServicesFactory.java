package org.components.factories;

import user.UserServices;

public class UserServicesFactory {
    public static UserServices.RegisterUserResponse createRegisterUserResponse(String message, boolean success, String userId) {
        return UserServices.RegisterUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .setUserId(userId)
                .build();
    }
    public static UserServices.LoginUserResponse createLoginUserResponse(String message, boolean success) {
        return UserServices.LoginUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();
    }
    public static UserServices.PurchaseTicketResponse createPurchaseTicketResponse(String message, boolean success) {
        return UserServices.PurchaseTicketResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();
    }
}
