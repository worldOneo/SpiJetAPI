package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.AsyncExecutor;
import de.worldoneo.spijetapi.utils.ScalingAsyncExecutor;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class SQLManager<T> implements SQLExecutor<T>, AsyncSQLExecutor<T> {
    protected static final AsyncExecutor asyncExecutor = new ScalingAsyncExecutor();

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

    @FunctionalInterface
    public interface SqlThrowingFunction<A, R> {
        R apply(A t) throws SQLException;
    }

    protected Supplier<CachedRowSet> tryOrThrow(SqlThrowingFunction<T, CachedRowSet> function, T arg) {
        return () -> {
            try {
                return function.apply(arg);
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        };
    }

    /**
     * Executes the executeUpdate function async and returns the completable future or null and a error
     *
     * @param arg the argument used to execute the update with
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    public CompletableFuture<CachedRowSet> executeUpdateAsync(T arg) {
        return CompletableFuture.supplyAsync(tryOrThrow(this::executeUpdate, arg), asyncExecutor.getThreadPoolExecutor());
    }

    /**
     * Executes the executeUpdate function async and returns the completable future or null and a error
     *
     * @param arg the argument used to execute the update with
     * @return The completable which is completed with a CachedRowSet or null if failed
     */
    public CompletableFuture<CachedRowSet> executeQueryAsync(T arg) {
        return CompletableFuture.supplyAsync(tryOrThrow(this::executeQuery, arg), asyncExecutor.getThreadPoolExecutor());
    }
}
