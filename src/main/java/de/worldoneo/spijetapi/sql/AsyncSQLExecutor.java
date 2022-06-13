package de.worldoneo.spijetapi.sql;

import de.worldoneo.spijetapi.utils.function.ThrowingConsumer;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public interface AsyncSQLExecutor {
    CompletableFuture<CachedRowSet> executeUpdateAsync(SQLExecutable arg);

    CompletableFuture<CachedRowSet> executeQueryAsync(SQLExecutable arg);

    CompletableFuture<Void> executeTransactionAsync(ThrowingConsumer<Connection, SQLException> transaction);
}
