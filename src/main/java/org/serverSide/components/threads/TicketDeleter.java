package org.serverSide.components.threads;

import java.util.concurrent.TimeUnit;

public class TicketDeleter extends Thread{

    private final ThreadManager tm;

    public TicketDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run(){
        tm.deleteTickets();
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
