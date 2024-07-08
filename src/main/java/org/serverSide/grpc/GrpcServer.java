package org.serverSide.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.serverSide.components.observers.JsonManagerObs;
import org.serverSide.components.singletonLists.*;
import org.serverSide.components.threads.CleanupManager;
import org.serverSide.components.threads.ScheduledCleanup;
import org.serverSide.components.units.Promo;
import org.serverSide.serverGUI.LoginPage;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = ThreadPoolManager.getExecutorService();
        // Build and start the gRPC server
        Server server = ServerBuilder.forPort(50051)
                .addService(new UserServicesImpl())
                .build();
        server.start();

        System.out.println("Server started, listening on port 50052");

        Timer time = new Timer();
        ScheduledCleanup sc = new ScheduledCleanup(new CleanupManager());
        JsonManagerObs jsonObserver = new JsonManagerObs();
        BookingList bookingList = BookingList.getInstance();
        UserList userList = UserList.getInstance();
        TicketList ticketList = TicketList.getInstance();
        FlightList flightList = FlightList.getInstance();
        PromoList promoList = PromoList.getInstance();
        AdministratorList administratorList = AdministratorList.getInstance();
        bookingList.attach(jsonObserver);
        userList.attach(jsonObserver);
        ticketList.attach(jsonObserver);
        flightList.attach(jsonObserver);
        promoList.attach(jsonObserver);
        administratorList.attach(jsonObserver);
        time.schedule(sc,0,86400000 );
        new LoginPage();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            if (server != null) {
                server.shutdown();
            }
            ThreadPoolManager.shutdown();
        }));

        server.awaitTermination();
    }
}
