package de.worldoneo.spijetapi.sql;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.SQLException;

public interface SQLExecutable {
    /**
     * Executes a query on the connection
     *
     * @param connection the connection to execute the query on
     * @return the RowSet as query result
     * @throws SQLException if an error occurred
     */
    CachedRowSet executeQuery(Connection connection) throws SQLException;

    /**
     * Executes an update on the connection
     *
     * @param connection the connection to run the update on
     * @return the generated keys as RowSet
     * @throws SQLException if an error occurred
     */
    CachedRowSet executeUpdate(Connection connection) throws SQLException;
}
