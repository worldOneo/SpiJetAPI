package de.worldoneo.spijetapi.sql;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.concurrent.Future;

public interface AsyncSQLExecutor<T> {
    Future<CachedRowSet> executeUpdateAsync(T arg) throws SQLException;

    Future<CachedRowSet> executeQueryAsync(T arg) throws SQLException;
}
