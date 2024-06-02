package org.threads;

public class TicketDeleter extends Thread{

    private final ThreadManager tm;

    public TicketDeleter(ThreadManager tm) {
        this.tm = tm;
    }

    public void run(){
        tm.deleteTickets();
    }
}
