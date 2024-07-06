package org.serverSide.grpc;

import io.grpc.stub.StreamObserver;
import org.serverSide.components.units.User;
import user.UserServices;

public class registerThread extends Thread{
    private UserServicesImpl grpc;
    private UserServices.RegisterUserRequest request;
    private StreamObserver<UserServices.RegisterUserResponse> responseObserver;

    public registerThread(UserServicesImpl grpc, UserServices.RegisterUserRequest request, StreamObserver<UserServices.RegisterUserResponse> responseObserver){
        this.grpc = grpc;
        this.request = request;
        this.responseObserver = responseObserver;
    }

    @Override
    public void run() {
        grpc.regUserThread(request, responseObserver);
    }
}
