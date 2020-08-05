package de.worldOneo.spiJetAPI.sql;

import javax.sql.rowset.CachedRowSet;

public interface SQLExecutor<T> {
    CachedRowSet executeUpdate(T arg);

    CachedRowSet executeQuery(T arg);
}
