package de.worldoneo.spijetapi.utils;

import lombok.Getter;

public class ScalingAsyncExecutor extends AsyncExecutor {
    @Getter
    private int maxThreads;
    public static final int DEFAULT_MAX_THREADS = 128;

    public ScalingAsyncExecutor() {
        this(Runtime.getRuntime().availableProcessors());
        this.maxThreads = DEFAULT_MAX_THREADS;
    }

    public ScalingAsyncExecutor(int corePoolSize) {
        this(corePoolSize, DEFAULT_MAX_THREADS);
    }

    public ScalingAsyncExecutor(int corePoolSize, int maxThreads) {
        super(corePoolSize);
        this.maxThreads = maxThreads;
        getThreadPoolExecutor().setMaximumPoolSize(maxThreads);
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
        getThreadPoolExecutor().setMaximumPoolSize(maxThreads);
    }
}
