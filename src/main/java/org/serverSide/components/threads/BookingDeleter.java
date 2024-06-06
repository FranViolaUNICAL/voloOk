package org.serverSide.components.threads;

import java.util.concurrent.TimeUnit;

public class BookingDeleter extends Thread {
    private final ThreadManager tm;

    public BookingDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run() {
        tm.deleteBooking();
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
