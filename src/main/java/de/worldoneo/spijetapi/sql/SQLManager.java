package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.AsyncExecutor;
import de.worldoneo.spijetapi.utils.ScalingAsyncExecutor;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import lombok.Getter;

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
}
