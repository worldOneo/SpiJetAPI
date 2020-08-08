package de.worldOneo.spiJetAPI.sql;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariDataSource;
import de.worldOneo.spiJetAPI.utils.SpiJetBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class SQLQueryBuilder extends AsyncSQLExecutorImpl<HikariDataSource> implements SpiJetBuilder<String>, SQLExecutor<HikariDataSource> {
    private final StringBuffer query;
    private final String database;
    private final Map<Integer, Object> parameterMap = new HashMap<>();

    public SQLQueryBuilder(String database, String sql) {
        this.query = new StringBuffer(sql);
        this.database = database;
    }

    @Override
    public CachedRowSet executeUpdate(HikariDataSource hikariDataSource) throws SQLException {
        CachedRowSet cachedRowSet;
        try (Connection connection = hikariDataSource.getConnection()) {
            selectDB(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }
            preparedStatement.executeUpdate();

            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.getGeneratedKeys());
        }
        return cachedRowSet;
    }

    @Override
    public CachedRowSet executeQuery(HikariDataSource hikariDataSource) throws SQLException {
        CachedRowSet cachedRowSet;
        try (Connection connection = hikariDataSource.getConnection()) {
            selectDB(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.executeQuery());
            preparedStatement.executeUpdate();
        }
        return cachedRowSet;
    }

    private void selectDB(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(String.format("USE %s;", database));
    }

    public SQLQueryBuilder setParameter(int key, Object value) {
        parameterMap.put(key, value);
        return this;
    }

    /**
     * I believe im to lazy to do.
     *
     * @return null
     */
    @Override
    public String build() {
        return query.toString();
    }

    @Override
    public Future<CachedRowSet> executeUpdateAsync(HikariDataSource arg) {
        return submit(() -> executeUpdate(arg));
    }

    @Override
    public Future<CachedRowSet> executeQueryAsync(HikariDataSource arg) {
        return submit(() -> executeQuery(arg));
    }
}
