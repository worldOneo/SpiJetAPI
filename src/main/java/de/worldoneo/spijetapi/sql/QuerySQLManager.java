package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import lombok.NonNull;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class QuerySQLManager extends SQLManager<SQLQueryBuilder> {
    private final HikariDataSource hikariDataSource;

    public QuerySQLManager(@NonNull HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    public QuerySQLManager(@NonNull HikariConfig hikariConfig) {
        this(new HikariDataSource(hikariConfig));
    }

    public QuerySQLManager(@NonNull SpiJetBuilder<HikariDataSource> dataSourceBuilder) {
        this(dataSourceBuilder.build());
    }

    @Override
    public CachedRowSet executeUpdate(SQLQueryBuilder sqlQueryBuilder) throws SQLException {
        return sqlQueryBuilder.executeUpdate(this.hikariDataSource);
    }

    @Override
    public CachedRowSet executeQuery(SQLQueryBuilder sqlQueryBuilder) throws SQLException {
        return sqlQueryBuilder.executeQuery(this.hikariDataSource);
    }

    public StringSQLManager toStringSQLManager() {
        return new StringSQLManager(this.hikariDataSource);
    }
}
