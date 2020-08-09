package de.worldOneo.spiJetAPI.sql;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldOneo.spiJetAPI.utils.SpiJetBuilder;
import lombok.NonNull;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Future;

public class StringSQLManager extends AsyncSQLExecutorImpl<String> implements SQLExecutor<String> {
    private final HikariDataSource hikariDataSource;

    public StringSQLManager(@NonNull HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    public StringSQLManager(@NonNull HikariConfig hikariConfig) {
        this(new HikariDataSource(hikariConfig));
    }

    public StringSQLManager(@NonNull SpiJetBuilder<HikariDataSource> dataSourceBuilder) {
        this(dataSourceBuilder.build());
    }

    @Override
    public Future<CachedRowSet> executeUpdateAsync(String arg) {
        return submit(() -> executeUpdate(arg));
    }

    @Override
    public Future<CachedRowSet> executeQueryAsync(String arg) {
        return submit(() -> executeQuery(arg));
    }

    @Override
    public CachedRowSet executeUpdate(String arg) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(arg, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.getResultSet());
            return cachedRowSet;
        }
    }

    @Override
    public CachedRowSet executeQuery(String arg) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(arg);
            CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.executeQuery());
            return cachedRowSet;
        }
    }

    public SQLManager toSqlManager() {
        return new SQLManager(hikariDataSource);
    }
}
