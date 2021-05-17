package de.worldoneo.spijetapi.sql;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public interface AsyncSQLExecutor<T> {
    CompletableFuture<CachedRowSet> executeUpdateAsync(T arg) throws SQLException;
    CompletableFuture<CachedRowSet> executeQueryAsync(T arg) throws SQLException;
}
