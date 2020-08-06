package de.worldOneo.spiJetAPI.sql;

import javax.sql.rowset.CachedRowSet;
import java.util.concurrent.Future;

public interface AsyncSQLExecutor<T> {
    Future<CachedRowSet> executeUpdateAsync(T arg);

    Future<CachedRowSet> executeQueryAsync(T arg);
}
