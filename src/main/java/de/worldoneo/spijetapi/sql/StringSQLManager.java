package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import lombok.NonNull;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StringSQLManager {
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

    public CachedRowSet executeUpdate(String arg) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(arg, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            CachedRowSet cachedRowSet = RowSetCreator.createRowSet();
            cachedRowSet.populate(preparedStatement.getResultSet());
            return cachedRowSet;
        }
    }


    public CachedRowSet executeQuery(String arg) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(arg);
            CachedRowSet cachedRowSet = RowSetCreator.createRowSet();
            cachedRowSet.populate(preparedStatement.executeQuery());
            return cachedRowSet;
        }
    }

    public QuerySQLManager toSqlManager() {
        return new QuerySQLManager(hikariDataSource);
    }
}
