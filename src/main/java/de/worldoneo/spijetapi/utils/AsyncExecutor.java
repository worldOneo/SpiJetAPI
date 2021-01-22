package de.worldoneo.spijetapi.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;

    public AsyncExecutor() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public AsyncExecutor(int corePoolSize) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return getThreadPoolExecutor().submit(callable);
    }

    public void submit(Runnable runnable) {
        getThreadPoolExecutor().submit(runnable);
    }

    public final ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setCorePoolSize(int corePoolSize) {
        getThreadPoolExecutor().setCorePoolSize(corePoolSize);
    }

    public int getCorePoolSize() {
        return getThreadPoolExecutor().getCorePoolSize();
    }
}
