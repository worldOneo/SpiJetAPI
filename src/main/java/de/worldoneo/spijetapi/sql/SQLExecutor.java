package de.worldoneo.spijetapi.sql;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public interface SQLExecutor<T> {
    CachedRowSet executeUpdate(T arg) throws SQLException;
    CachedRowSet executeQuery(T arg) throws SQLException;
}
