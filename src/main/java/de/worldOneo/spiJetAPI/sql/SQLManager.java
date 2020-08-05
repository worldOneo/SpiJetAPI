package de.worldOneo.spiJetAPI.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;

import javax.sql.rowset.CachedRowSet;

public class SQLManager implements SQLExecutor<SQLQueryBuilder> {
    private final HikariDataSource hikariDataSource;

    public SQLManager(@NonNull HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    public SQLManager(@NonNull HikariConfig hikariConfig) {
        this(new HikariDataSource(hikariConfig));
    }

    public SQLManager(@NonNull DataSourceBuilder dataSourceBuilder) {
        this(dataSourceBuilder.build());
    }

    @Override
    public CachedRowSet executeUpdate(SQLQueryBuilder sqlQueryBuilder) {
        return sqlQueryBuilder.executeUpdate(hikariDataSource);
    }

    @Override
    public CachedRowSet executeQuery(SQLQueryBuilder sqlQueryBuilder) {
        return sqlQueryBuilder.executeQuery(hikariDataSource);
    }
}
