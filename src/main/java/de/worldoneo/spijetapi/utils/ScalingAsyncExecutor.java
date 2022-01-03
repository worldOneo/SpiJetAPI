package de.worldoneo.spijetapi.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @deprecated Use the java concurrent API or task API instead
 */
public class ScalingAsyncExecutor extends AsyncExecutor {
    public static final int DEFAULT_MAX_THREADS = 64;

    public ScalingAsyncExecutor() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public ScalingAsyncExecutor(int corePoolSize) {
        this(corePoolSize, DEFAULT_MAX_THREADS);
    }

    public ScalingAsyncExecutor(int corePoolSize, int maxThreads) {
        super((ThreadPoolExecutor) Executors.newCachedThreadPool());
        setMaxThreads(maxThreads);
        getThreadPoolExecutor().setCorePoolSize(corePoolSize);
    }

    public int getMaxThreads() {
        return getThreadPoolExecutor().getMaximumPoolSize();
    }

    public void setMaxThreads(int maxThreads) {
        getThreadPoolExecutor().setMaximumPoolSize(maxThreads);
    }
}
