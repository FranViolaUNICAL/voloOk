package org.serverSide.components.threads;

import java.util.TimerTask;

public class ScheduledCleanup extends TimerTask {

    CleanupManager c;

    public ScheduledCleanup(CleanupManager c){
        this.c = c;
    }
    @Override
    public void run() {
        try {
            c.cleanup();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
