package de.worldOneo.spiJetAPI.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class ScalingAsyncExecutor extends AsyncExecutor {

    public ScalingAsyncExecutor() {
        super();
    }

    public ScalingAsyncExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public <T> Future<T> submit(Callable<T> cachedRowSetCallable) {
        if (getThreadPoolExecutor().getQueue().size() >= getThreadPoolExecutor().getMaximumPoolSize()) {
            getThreadPoolExecutor().setMaximumPoolSize(getThreadPoolExecutor().getMaximumPoolSize() + 1);
        }
        return getThreadPoolExecutor().submit(cachedRowSetCallable);
    }
}
