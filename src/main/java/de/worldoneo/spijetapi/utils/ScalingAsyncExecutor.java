package de.worldoneo.spijetapi.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ScalingAsyncExecutor extends AsyncExecutor {
    @Getter
    @Setter
    private int maxThreads;
    public static final int DEFAULT_MAX_THREADS = 128;

    public ScalingAsyncExecutor() {
        super();
        this.maxThreads = DEFAULT_MAX_THREADS;
    }

    public ScalingAsyncExecutor(int corePoolSize) {
        this(corePoolSize, DEFAULT_MAX_THREADS);
    }

    public ScalingAsyncExecutor(int corePoolSize, int maxThreads) {
        super(corePoolSize);
        this.maxThreads = maxThreads;
    }

    @Override
    public <T> Future<T> submit(Callable<T> cachedRowSetCallable) {
        getThreadPoolExecutor().setMaximumPoolSize(maxThreads);
        return getThreadPoolExecutor().submit(cachedRowSetCallable);
    }
}
