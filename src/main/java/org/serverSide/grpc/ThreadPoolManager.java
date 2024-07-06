package org.serverSide.grpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static ExecutorService getExecutorService(){
        return executorService;
    }

    public static void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
