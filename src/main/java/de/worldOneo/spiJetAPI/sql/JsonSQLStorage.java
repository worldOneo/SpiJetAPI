package de.worldOneo.spiJetAPI.sql;

import com.google.gson.Gson;
import de.worldOneo.spiJetAPI.utils.AsyncExecutor;
import lombok.Getter;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.UUID;

public class JsonSQLStorage extends AsyncExecutor {
    private final SQLExecutor<SQLQueryBuilder> sqlExecutor;
    private final String tableName;
    private final String databaseName;
    private static final Gson GSON = new Gson();

    @Getter
    private enum SQLStrings {
        TABLE_CREATION_STRING("CREATE TABLE IF NOT EXISTS `%s` (" +
                "`uuid` TINYTEXT NOT NULL," +
                "`jsonDocument` LONGTEXT NOT NULL COLLATE 'utf8mb4_bin'," +
                "UNIQUE INDEX `UniqueID` (`uuid`(40))" +
                ");"),
        GETTER_STRING("SELECT * from `%s` WHERE uuid='%s';"),
        SETTER_STRING("INSERT INTO `%s` (uuid, jsonDocument) VALUES ('%s', '%3$s') ON DUPLICATE KEY UPDATE jsonDocument='%3$s';");
        final String string;

        SQLStrings(String string) {
            this.string = string;
        }
    }

    public JsonSQLStorage(SQLExecutor<SQLQueryBuilder> sqlExecutor, String tableName, String databaseName) {
        this.sqlExecutor = sqlExecutor;
        this.tableName = tableName;
        this.databaseName = databaseName;
        setup();
    }

    private void setup() {
        String formattedCreationString = String.format(SQLStrings.TABLE_CREATION_STRING.getString(), tableName);
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(databaseName, formattedCreationString);
        sqlExecutor.executeUpdate(sqlQueryBuilder);
    }

    public <T> T getData(UUID uuid, Class<T> classOfT) {
        String formattedString = String.format(SQLStrings.SETTER_STRING.getString(), tableName, uuid.toString());
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(databaseName, formattedString);
        CachedRowSet cachedRowSet = sqlExecutor.executeQuery(sqlQueryBuilder);
        if (cachedRowSet == null) {
            return null;
        }
        try {
            return GSON.fromJson(cachedRowSet.getString("json"), classOfT);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean setData(UUID uuid, Object dataObject) {
        String data = GSON.toJson(dataObject);
        String format = String.format(SQLStrings.SETTER_STRING.getString(), tableName, uuid.toString(), data);
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(databaseName, format);
        CachedRowSet cachedRowSet = sqlExecutor.executeUpdate(sqlQueryBuilder);
        return cachedRowSet != null;
    }
}
