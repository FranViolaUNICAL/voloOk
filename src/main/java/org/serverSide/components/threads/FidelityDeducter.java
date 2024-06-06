package org.serverSide.components.threads;

import java.util.concurrent.TimeUnit;

public class FidelityDeducter extends Thread{
    private final ThreadManager tm;

    public FidelityDeducter(ThreadManager tm){
        this.tm = tm;
    }

    @Override
    public void run() {
        tm.deductFidelityPoints();
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
