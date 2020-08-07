package de.worldOneo.spiJetAPI.sql;

import de.worldOneo.spiJetAPI.utils.AsyncExecutor;

import javax.sql.rowset.CachedRowSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class AsyncSQLExecutorImpl<T> extends AsyncExecutor implements AsyncSQLExecutor<T> {
    public Future<CachedRowSet> submit(Callable<CachedRowSet> cachedRowSetCallable) {
        return getExecutorService().submit(cachedRowSetCallable);
    }
}
