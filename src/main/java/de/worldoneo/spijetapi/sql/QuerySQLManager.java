package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import de.worldoneo.spijetapi.utils.function.ThrowingConsumer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

@Getter
@Setter
public class QuerySQLManager extends SQLManager {
    private final HikariDataSource hikariDataSource;
    private ExecutorService asyncExecutor = SQLManager.defaultAsyncExecutor;

    public QuerySQLManager(@NonNull HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    public QuerySQLManager(@NonNull HikariConfig hikariConfig) {
        this(new HikariDataSource(hikariConfig));
    }

    public QuerySQLManager(@NonNull SpiJetBuilder<HikariDataSource> dataSourceBuilder) {
        this(dataSourceBuilder.build());
    }

    public StringSQLManager toStringSQLManager() {
        return new StringSQLManager(hikariDataSource);
    }

    @Override
    public CachedRowSet executeUpdate(SQLExecutable executable) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            return executable.executeUpdate(connection);
        }
    }

    @Override
    public CachedRowSet executeQuery(SQLExecutable executable) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            return executable.executeQuery(connection);
        }
    }

    @Override
    public void executeTransaction(ThrowingConsumer<Connection, SQLException> transaction) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            connection.setAutoCommit(false);
            transaction.accept(connection);
            connection.commit();
        }
    }
}
