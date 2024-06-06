package org.clientSide;

import io.grpc.Channel;
import user.UserServiceGrpc;

public class Client {
    private final UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public Client(Channel channel){
        this.blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }


}
