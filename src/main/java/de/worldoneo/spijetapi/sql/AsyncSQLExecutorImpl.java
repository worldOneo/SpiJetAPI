package de.worldoneo.spijetapi.sql;

import de.worldoneo.spijetapi.utils.ScalingAsyncExecutor;

public abstract class AsyncSQLExecutorImpl<T> extends ScalingAsyncExecutor implements AsyncSQLExecutor<T> {

    public AsyncSQLExecutorImpl() {
        super(Runtime.getRuntime().availableProcessors());
    }
}
