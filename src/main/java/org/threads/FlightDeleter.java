package org.threads;

public class FlightDeleter extends Thread{
    private ThreadManager tm;

    public FlightDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run() {
        tm.deleteFlights();
    }
}
