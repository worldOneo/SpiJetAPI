package de.worldoneo.spijetapi.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;

    public AsyncExecutor() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public AsyncExecutor(int corePoolSize) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return getThreadPoolExecutor().submit(callable);
    }
    
    protected ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
