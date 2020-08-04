package de.worldOneo.spiJetAPI.sql;

import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SQLQueryBuilder {
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

            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("USE %s;", database));

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            for (Map.Entry<Integer, Object> objectEntry : parameterMap.entrySet()) {
                preparedStatement.setObject(objectEntry.getKey(), objectEntry.getValue());
            }
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(preparedStatement.getGeneratedKeys());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cachedRowSet;
    }

    public SQLQueryBuilder setParameter(int key, Object value) {
        parameterMap.put(key, value);
        return this;
    }

}
