package org.threads;

import java.util.concurrent.TimeUnit;

public class FlightDeleter extends Thread{
    private final ThreadManager tm;

    public FlightDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run() {
        tm.deleteFlights();
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
