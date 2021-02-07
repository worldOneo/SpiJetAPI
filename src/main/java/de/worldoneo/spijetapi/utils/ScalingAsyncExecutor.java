package de.worldoneo.spijetapi.utils;

public class ScalingAsyncExecutor extends AsyncExecutor {
    public static final int DEFAULT_MAX_THREADS = 64;

    public ScalingAsyncExecutor() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public ScalingAsyncExecutor(int corePoolSize) {
        this(corePoolSize, DEFAULT_MAX_THREADS);
    }

    public ScalingAsyncExecutor(int corePoolSize, int maxThreads) {
        super(corePoolSize);
        setMaxThreads(maxThreads);
    }

    public void setMaxThreads(int maxThreads) {
        getThreadPoolExecutor().setMaximumPoolSize(maxThreads);
    }

    public int getMaxThreads() {
        return getThreadPoolExecutor().getMaximumPoolSize();
    }
}
