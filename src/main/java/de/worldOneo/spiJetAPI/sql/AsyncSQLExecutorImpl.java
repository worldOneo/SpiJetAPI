package de.worldOneo.spiJetAPI.sql;

import javax.sql.rowset.CachedRowSet;
import java.util.concurrent.*;

public abstract class AsyncSQLExecutorImpl<T> implements AsyncSQLExecutor<T> {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static ExecutorService getExecutorService() {
        return executorService;
    }

    public Future<CachedRowSet> submit(Callable<CachedRowSet> cachedRowSetCallable) {
        return executorService.submit(cachedRowSetCallable);
    }
}
