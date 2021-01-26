package de.worldoneo.spijetapi.sql;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.AsyncExecutor;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Future;

public class JsonSQLStorage {
    private final AsyncExecutor asyncExecutor = SQLManager.asyncExecutor;
    private final SQLExecutor<SQLQueryBuilder> sqlExecutor;
    private final String tableName;
    private static final Gson GSON = new Gson();

    @Getter
    private enum SQLStrings {
        TABLE_CREATION_STRING("CREATE TABLE IF NOT EXISTS `%s` (" +
                "`uuid` TINYTEXT NOT NULL," +
                "`jsonDocument` JSON NOT NULL COLLATE 'utf8mb4_bin'," +
                "UNIQUE INDEX `UniqueID` (`uuid`(40))" +
                ");"),
        GETTER_STRING("SELECT * from `%s` WHERE uuid='%s';"),
        SETTER_STRING("INSERT INTO `%s` (uuid, jsonDocument) VALUES ('%s', '%3$s') ON DUPLICATE KEY UPDATE jsonDocument='%3$s';");
        final String string;

        SQLStrings(String string) {
            this.string = string;
        }
    }

    public JsonSQLStorage(SQLExecutor<SQLQueryBuilder> sqlExecutor, String tableName) throws SQLException {
        this.sqlExecutor = sqlExecutor;
        this.tableName = tableName;
        setup();
    }

    public JsonSQLStorage(SpiJetBuilder<HikariDataSource> dataSourceBuilder, String tableName) throws SQLException {
        this(new QuerySQLManager(dataSourceBuilder), tableName);
    }

    public JsonSQLStorage(HikariDataSource hikariDataSource, String tableName) throws SQLException {
        this(new QuerySQLManager(hikariDataSource), tableName);
    }

    /**
     * Tries to create a table based on the name.
     *
     * @throws SQLException if an error occurred while creating the table.
     */
    private void setup() throws SQLException {
        String formattedCreationString = String.format(SQLStrings.TABLE_CREATION_STRING.getString(), tableName);
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(formattedCreationString);
        sqlExecutor.executeUpdate(sqlQueryBuilder);
    }

    /**
     * Gets the JSON item out of the database.
     *
     * @param uuid     the uuid this JSON item is identified with.
     * @param classOfT the class of the JSON item to be parsed to.
     * @param <T>      the class of th JSON item to be parsed to.
     * @return The parsed Class or null if the class couldn't be found.
     * @throws SQLException when there is an error while getting the data from the database.
     */
    @Nullable
    public <T> T getData(UUID uuid, Class<T> classOfT) throws SQLException {
        String formattedString = String.format(SQLStrings.GETTER_STRING.getString(), tableName, uuid.toString());
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(formattedString);
        CachedRowSet cachedRowSet = sqlExecutor.executeQuery(sqlQueryBuilder);
        if (cachedRowSet == null) {
            return null;
        }
        if (!cachedRowSet.next()) {
            return null;
        }
        return GSON.fromJson(cachedRowSet.getString("jsonDocument"), classOfT);
    }

    /**
     * Inserts or update the data into the table with the uuid as its identifier
     *
     * @param uuid       The uuid the JSON item is identified with.
     * @param dataObject the object to write into the table.
     * @return the generated rows.
     * @throws SQLException if an error occurred while inserting or updating the data into the table.
     */
    public CachedRowSet setData(UUID uuid, Object dataObject) throws SQLException {
        String data = GSON.toJson(dataObject);
        String format = String.format(SQLStrings.SETTER_STRING.getString(), tableName, uuid.toString(), data);
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder(format);
        return sqlExecutor.executeUpdate(sqlQueryBuilder);
    }

    public Future<CachedRowSet> setDataAsync(UUID uuid, Object dataObject) {
        return asyncExecutor.submit(() -> setData(uuid, dataObject));
    }

    public <T> Future<T> getDataAsync(UUID uuid, Class<T> classOfT) {
        return asyncExecutor.submit(() -> getData(uuid, classOfT));
    }
}
