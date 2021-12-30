package de.worldoneo.spijetapi.sql;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Setter
@Getter
@Accessors(chain = true)
public class SQLQueryBuilder implements SQLExecutable {
    private ExecutorService asyncExecutor = SQLManager.defaultAsyncExecutor;
    private String query;
    private Map<Integer, Object> parameterMap = new HashMap<>();

    public SQLQueryBuilder(String sql) {
        this.query = sql;
    }

    public SQLQueryBuilder(String sql, Object... parameters) {
        this(sql);
        setParameters(parameters);
    }

    @Override
    public CachedRowSet executeUpdate(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

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

    /**
     * Sets a SQL parameters
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
     * Sets the parameters to the SQL parameters in the order provided
     * starting with index 1
     *
     * @param parameters the SQL Query parameters
     * @return this
     */
    public SQLQueryBuilder setParameters(Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            setParameter(i + 1, parameters);
        }
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

    @Override
    public CachedRowSet executeQuery(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
}
