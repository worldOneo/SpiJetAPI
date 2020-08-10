package de.worldOneo.spiJetAPI.sql;

import de.worldOneo.spiJetAPI.utils.AsyncExecutor;

import javax.sql.rowset.CachedRowSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class AsyncSQLExecutorImpl<T> extends AsyncExecutor implements AsyncSQLExecutor<T> {

    public AsyncSQLExecutorImpl() {
        super(Runtime.getRuntime().availableProcessors());
    }

    public Future<CachedRowSet> submit(Callable<CachedRowSet> cachedRowSetCallable) {
        if (getThreadPoolExecutor().getQueue().size() >= getThreadPoolExecutor().getMaximumPoolSize()) {
            getThreadPoolExecutor().setMaximumPoolSize(getThreadPoolExecutor().getMaximumPoolSize() + 1);
        }
        return getThreadPoolExecutor().submit(cachedRowSetCallable);
    }
}
