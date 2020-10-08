package de.worldoneo.spijetapi.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ScalingAsyncExecutor extends AsyncExecutor {

    public ScalingAsyncExecutor() {
        super();
    }

    public ScalingAsyncExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public <T> Future<T> submit(Callable<T> cachedRowSetCallable) {
        if (getThreadPoolExecutor().getActiveCount()+getThreadPoolExecutor().getQueue().size() >= getThreadPoolExecutor().getMaximumPoolSize()) {
            getThreadPoolExecutor().setMaximumPoolSize(getThreadPoolExecutor().getMaximumPoolSize() + 1);
        }
        return getThreadPoolExecutor().submit(cachedRowSetCallable);
    }
}
