package de.worldoneo.spijetapi.sql;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class RowSetCreator {
    private static final RowSetCreator INSTANCE = new RowSetCreator();
    private final RowSetFactory factory;

    private RowSetCreator() {
        RowSetFactory setFactory;
        try {
            setFactory = RowSetProvider.newFactory();
        } catch (SQLException ignored) {
            setFactory = null;
        }
        factory = setFactory;
    }

    public static RowSetCreator getInstance() {
        return INSTANCE;
    }

    public static CachedRowSet createRowSet() throws SQLException {
        if (INSTANCE.factory == null) throw new SQLException("Couldn't create RowSetFactory!");

        return INSTANCE.factory.createCachedRowSet();
    }
}
