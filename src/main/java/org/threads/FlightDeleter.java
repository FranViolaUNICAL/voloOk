package org.threads;

public class FlightDeleter extends Thread{
    private final ThreadManager tm;

    public FlightDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run() {
        tm.deleteFlights();
    }
}
