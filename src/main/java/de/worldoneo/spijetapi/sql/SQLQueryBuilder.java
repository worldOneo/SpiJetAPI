package de.worldoneo.spijetapi.sql;

import de.worldoneo.spijetapi.utils.AsyncExecutor;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Setter
@Getter
@Accessors(chain = true)
public class SQLQueryBuilder implements SpiJetBuilder<SQLQueryBuilder>, SQLExecutor<DataSource> {
    private AsyncExecutor asyncExecutor = SQLManager.defaultAsyncExecutor;
    private StringBuffer query;
    private Map<Integer, Object> parameterMap = new HashMap<>();

    public SQLQueryBuilder(String sql) {
        this.query = new StringBuffer(sql);
    }

    @Override
    public CachedRowSet executeUpdate(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {

            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }
            preparedStatement.executeUpdate();

            CachedRowSet cachedRowSet = RowSetCreator.createRowSet();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            cachedRowSet.populate(generatedKeys);
            generatedKeys.close();
            return cachedRowSet;
        }
    }

    @Override
    public CachedRowSet executeQuery(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }

            CachedRowSet cachedRowSet = RowSetCreator.createRowSet();
            ResultSet data = preparedStatement.executeQuery();
            cachedRowSet.populate(data);
            data.close();
            return cachedRowSet;
        }
    }

    /**
     * Executes a SQL update and calls the consumer with the result
     *
     * @param dataSource the datasource to get the connection from
     * @param consumer   the consumer used to pass the result to
     * @deprecated No error handling use a SQLManager instead
     */
    @Deprecated
    public void executeUpdate(DataSource dataSource, Consumer<CachedRowSet> consumer) {
        try {
            CachedRowSet cachedRowSet = executeUpdate(dataSource);
            consumer.accept(cachedRowSet);
        } catch (SQLException ignored) {
        }
    }

    /**
     * Executes a SQL query and calls the consumer with the result
     *
     * @param dataSource the datasource to get the connection from
     * @param consumer   the consumer used to pass the result to
     * @deprecated No error handling use a SQLManager instead
     */
    @Deprecated
    public void executeQuery(DataSource dataSource, Consumer<CachedRowSet> consumer) {
        try {
            CachedRowSet cachedRowSet = executeQuery(dataSource);
            consumer.accept(cachedRowSet);
        } catch (SQLException ignored) {
        }
    }

    /**
     * Executes a SQL query async and calls the consumer with the result
     *
     * @param dataSource the datasource to get the connection from
     * @param consumer   the consumer used to pass the result to
     * @deprecated No error handling use a SQLManager instead
     */
    @Deprecated
    public void executeQueryAsync(DataSource dataSource, Consumer<CachedRowSet> consumer) {
        asyncExecutor.submit(() -> executeQuery(dataSource, consumer));
    }

    /**
     * Executes a SQL update async and calls the consumer with the result
     *
     * @param dataSource the datasource to get the connection from
     * @param consumer   the consumer used to pass the result to
     * @deprecated No error handling use a SQLManager instead
     */
    @Deprecated
    public void executeUpdateAsync(DataSource dataSource, Consumer<CachedRowSet> consumer) {
        asyncExecutor.submit(() -> executeUpdate(dataSource, consumer));
    }

    /**
     * Sets a SQL parameter written as ?
     *
     * @param key   the index starting at 1
     * @param value the value to write
     * @return this
     */
    public SQLQueryBuilder setParameter(int key, Object value) {
        parameterMap.put(key, value);
        return this;
    }

    /**
     * Returns the parameter of the index or {@code null} if not set
     *
     * @param key the index of the parameter
     * @return the parameter or null
     */
    @Nullable
    public Object getParameter(int key) {
        return parameterMap.get(key);
    }

    /**
     * Creates a clone of this SQLQueryBuilder
     *
     * @return the clone of this SQLQueryBuilder
     */
    @Override
    public SQLQueryBuilder build() {
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(query.toString());
        for (Map.Entry<Integer, Object> parameter : parameterMap.entrySet()) {
            sqlQueryBuilder.setParameter(parameter.getKey(), parameter.getValue());
        }
        sqlQueryBuilder.setAsyncExecutor(this.getAsyncExecutor());
        return sqlQueryBuilder;
    }

    /**
     * @param arg The datasource to get the connection from
     * @return the future of this update
     * @deprecated No error handling use a SQLManager instead
     */
    @Deprecated
    public Future<CachedRowSet> executeUpdateAsync(DataSource arg) {
        return asyncExecutor.submit(() -> executeUpdate(arg));
    }

    /**
     * @param arg The datasource to get the connection from
     * @return the future of this query
     * @deprecated No error handling use a SQLManager instead
     */
    @Deprecated
    public Future<CachedRowSet> executeQueryAsync(DataSource arg) {
        return asyncExecutor.submit(() -> executeQuery(arg));
    }
}
