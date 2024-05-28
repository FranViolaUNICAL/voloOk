package org.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.Main;
import org.users.UserServiceImpl;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Build and start the gRPC server
        Server server = ServerBuilder.forPort(8080)
                .addService(new UserServiceImpl())
                .build();
        server.start();
        System.out.println("Server started, listening on port 8080");
        // Register a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            if (server != null) {
                server.shutdown();
            }
        }));

        // Keep the server running
        server.awaitTermination();
    }
}
