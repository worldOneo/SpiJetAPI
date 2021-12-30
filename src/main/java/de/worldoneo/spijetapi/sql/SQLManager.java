package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.tasks.LazyTaskPool;
import de.worldoneo.spijetapi.utils.RuntimeErrorWrapper;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import de.worldoneo.spijetapi.utils.function.ThrowingConsumer;
import lombok.Getter;
import lombok.Setter;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public abstract class SQLManager implements SQLExecutor, AsyncSQLExecutor {
    protected static final ExecutorService defaultAsyncExecutor = LazyTaskPool.commonThreadPool();
    @Getter
    @Setter
    private ExecutorService asyncExecutor = SQLManager.defaultAsyncExecutor;

    public static StringSQLManager createStringManager(HikariDataSource hikariDataSource) {
        return new StringSQLManager(hikariDataSource);
    }

    public static StringSQLManager createStringManager(SpiJetBuilder<HikariDataSource> hikariDataSourceBuilder) {
        return createStringManager(hikariDataSourceBuilder.build());
    }

    public static SQLManager createQueryManager(HikariDataSource hikariDataSource) {
        return new QuerySQLManager(hikariDataSource);
    }

    public static SQLManager createQueryManager(SpiJetBuilder<HikariDataSource> hikariDataSourceBuilder) {
        return createQueryManager(hikariDataSourceBuilder.build());
    }

    public static SQLManager createQueryManager(HikariConfig hikariConfig) {
        return new QuerySQLManager(hikariConfig);
    }

    /**
     * Executes the executable async and returns the completable future
     * which is completed which the fetched {@link javax.sql.RowSet} as {@link CachedRowSet}
     *
     * @param arg the executable to query by this manager
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    public CompletableFuture<CachedRowSet> executeUpdateAsync(SQLExecutable arg) {
        return RuntimeErrorWrapper.tryOrThrow(this::executeUpdate, arg, getAsyncExecutor());
    }

    /**
     * Executes the executable async and returns the completable future
     * which is completed with the generated rows as {@link CachedRowSet}
     *
     * @param arg the executable to execute by this manager
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    public CompletableFuture<CachedRowSet> executeQueryAsync(SQLExecutable arg) {
        return RuntimeErrorWrapper.tryOrThrow(this::executeQuery, arg, getAsyncExecutor());
    }

    /**
     * Executes the given consumer async and returns the completable future
     * representing the pending transaction
     *
     * @param transaction the transaction to run
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    @Override
    public CompletableFuture<Void> executeTransactionAsync(ThrowingConsumer<Connection, SQLException> transaction) {
        return CompletableFuture.supplyAsync(() -> {
            RuntimeErrorWrapper.wrap(() -> executeTransaction(transaction)).run();
            return null;
        }, getAsyncExecutor());
    }
}
