package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.AsyncExecutor;
import de.worldoneo.spijetapi.utils.RuntimeErrorWrapper;
import de.worldoneo.spijetapi.utils.ScalingAsyncExecutor;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import lombok.Getter;
import lombok.Setter;

import javax.sql.rowset.CachedRowSet;
import java.util.concurrent.CompletableFuture;

public abstract class SQLManager<T> implements SQLExecutor<T>, AsyncSQLExecutor<T> {
    protected static final AsyncExecutor defaultAsyncExecutor = new ScalingAsyncExecutor();

    @Getter
    @Setter
    private AsyncExecutor asyncExecutor = SQLManager.defaultAsyncExecutor;

    public static SQLManager<String> createStringManager(HikariDataSource hikariDataSource) {
        return new StringSQLManager(hikariDataSource);
    }

    public static SQLManager<String> createStringManager(SpiJetBuilder<HikariDataSource> hikariDataSourceBuilder) {
        return createStringManager(hikariDataSourceBuilder.build());
    }

    public static SQLManager<SQLQueryBuilder> createQueryManager(HikariDataSource hikariDataSource) {
        return new QuerySQLManager(hikariDataSource);
    }

    public static SQLManager<SQLQueryBuilder> createQueryManager(SpiJetBuilder<HikariDataSource> hikariDataSourceBuilder) {
        return createQueryManager(hikariDataSourceBuilder.build());
    }

    public static SQLManager<SQLQueryBuilder> createQueryManager(HikariConfig hikariConfig) {
        return new QuerySQLManager(hikariConfig);
    }

    public static SQLManager<String> createStringManager(HikariConfig hikariConfig) {
        return new StringSQLManager(hikariConfig);
    }

    /**
     * Executes the executeUpdate function async and returns the completable future
     * which is completed which the fetched {@link javax.sql.RowSet} as {@link CachedRowSet}
     *
     * @param arg the argument used to execute the update with
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    public CompletableFuture<CachedRowSet> executeUpdateAsync(T arg) {
        return RuntimeErrorWrapper.tryOrThrow(this::executeUpdate, arg, this.getAsyncExecutor().getThreadPoolExecutor());
    }

    /**
     * Executes the executeQuery function async and returns the completable future
     * which is completed with the generated rows as {@link CachedRowSet}
     *
     * @param arg the argument used to execute the update with
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    public CompletableFuture<CachedRowSet> executeQueryAsync(T arg) {
        return RuntimeErrorWrapper.tryOrThrow(this::executeQuery, arg, this.getAsyncExecutor().getThreadPoolExecutor());
    }
}
