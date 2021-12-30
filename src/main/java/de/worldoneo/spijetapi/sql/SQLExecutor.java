package de.worldoneo.spijetapi.sql;

import de.worldoneo.spijetapi.utils.function.ThrowingConsumer;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.SQLException;

public interface SQLExecutor {
    CachedRowSet executeUpdate(SQLExecutable executable) throws SQLException;

    CachedRowSet executeQuery(SQLExecutable executable) throws SQLException;

    void executeTransaction(ThrowingConsumer<Connection, SQLException> transaction) throws SQLException;
}
