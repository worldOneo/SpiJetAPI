package de.worldOneo.spiJetAPI.sql;

import de.worldOneo.spiJetAPI.utils.ScalingAsyncExecutor;

public abstract class AsyncSQLExecutorImpl<T> extends ScalingAsyncExecutor implements AsyncSQLExecutor<T> {

    public AsyncSQLExecutorImpl() {
        super(Runtime.getRuntime().availableProcessors());
    }
}
