package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class SQLQueryBuilder extends AsyncSQLExecutorImpl<HikariDataSource> implements SpiJetBuilder<SQLQueryBuilder>, SQLExecutor<HikariDataSource> {
    private final StringBuffer query;
    private final Map<Integer, Object> parameterMap = new HashMap<>();

    public SQLQueryBuilder(String sql) {
        this.query = new StringBuffer(sql);
    }

    @Override
    public CachedRowSet executeUpdate(HikariDataSource hikariDataSource) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }
            preparedStatement.executeUpdate();

            CachedRowSet cachedRowSet = RowSetCreator.createRowSet();
            cachedRowSet.populate(preparedStatement.getGeneratedKeys());
            return cachedRowSet;
        }
    }

    @Override
    public CachedRowSet executeQuery(HikariDataSource hikariDataSource) throws SQLException {
        try (Connection connection = hikariDataSource.getConnection()) {
            //selectDB(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }

            CachedRowSet cachedRowSet = RowSetCreator.createRowSet();
            cachedRowSet.populate(preparedStatement.executeQuery());
            return cachedRowSet;
        }
    }

    /**
     * Sets a SQL parameter written as ?
     * @param key the index starting at 1
     * @param value the value to write
     * @return this
     */
    public SQLQueryBuilder setParameter(int key, Object value) {
        parameterMap.put(key, value);
        return this;
    }

    /**
     * I believe im to lazy to do.
     *
     * @return this
     */
    @Override
    public SQLQueryBuilder build() {
        return this;
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
