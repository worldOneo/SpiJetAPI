package de.worldOneo.spiJetAPI.sql;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class RowSetCreator {
    private static final RowSetCreator instance = new RowSetCreator();
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
        return instance;
    }

    public static CachedRowSet createRowSet() throws SQLException {
        if (instance.factory == null) throw new SQLException("Couldn't create RowSetFactory!");

        return instance.factory.createCachedRowSet();
    }
}
