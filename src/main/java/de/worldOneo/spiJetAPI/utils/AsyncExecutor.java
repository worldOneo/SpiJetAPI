package de.worldOneo.spiJetAPI.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutor {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    protected static ExecutorService getExecutorService() {
        return executorService;
    }
}
