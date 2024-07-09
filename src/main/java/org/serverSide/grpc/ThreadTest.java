package org.serverSide.grpc;

import java.util.concurrent.TimeUnit;

public class ThreadTest extends Thread{
    @Override
    public void run() {
        System.out.println("Hello!");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello again!");
    }
}
