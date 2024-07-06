package org.clientSide;

public class promoThread extends Thread{
    private Client c;

    public promoThread(Client c){
        this.c = c;
    }

    @Override
    public void run() {
        c.sendNotifications(c.isLoggedIn());
    }
}
