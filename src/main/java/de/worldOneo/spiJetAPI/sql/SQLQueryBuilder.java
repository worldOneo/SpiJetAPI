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

public class SQLQueryBuilder implements SpiJetBuilder<String> {
    private final StringBuffer query;
    private final String database;
    private final Map<Integer, Object> parameterMap = new HashMap<>();

    public SQLQueryBuilder(String database, String sql) {
        this.query = new StringBuffer(sql);
        this.database = database;
    }

    public CachedRowSet executeUpdate(HikariDataSource hikariDataSource) {
        CachedRowSet cachedRowSet = null;
        try (Connection connection = hikariDataSource.getConnection()) {
            selectDB(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }
            preparedStatement.executeUpdate();

            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.getGeneratedKeys());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cachedRowSet;
    }

    public CachedRowSet executeQuery(HikariDataSource hikariDataSource) {
        CachedRowSet cachedRowSet = null;
        try (Connection connection = hikariDataSource.getConnection()) {
            selectDB(connection);

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }

            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.executeQuery());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
}
