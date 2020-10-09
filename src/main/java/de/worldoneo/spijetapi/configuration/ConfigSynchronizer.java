package de.worldoneo.spijetapi.configuration;

import de.worldoneo.spijetapi.sql.SQLExecutor;
import de.worldoneo.spijetapi.sql.SQLQueryBuilder;
import org.jetbrains.annotations.Nullable;

import javax.sql.RowSet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Base64;

public class ConfigSynchronizer {
    public static final String CREATE_CODE = "CREATE TABLE IF NOT EXISTS `%s` (" +
            "`file` TEXT(65535) NOT NULL COLLATE 'utf8_bin'," +
            "`content` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8_bin'," +
            "UNIQUE INDEX `name` (`file`(50)) USING BTREE" +
            ")" +
            "COLLATE='utf8_bin'" +
            "ENGINE=InnoDB" +
            ";";

    private final SQLExecutor<SQLQueryBuilder> sqlExecutor;
    private final String table;

    public ConfigSynchronizer(SQLExecutor<SQLQueryBuilder> sqlExecutor, String table, boolean setup) throws SQLException {
        this.sqlExecutor = sqlExecutor;
        this.table = table;
        if (setup) {
            this.sqlExecutor.executeUpdate(new SQLQueryBuilder(String.format(CREATE_CODE, table)));
        }
    }

    public ConfigSynchronizer(SQLExecutor<SQLQueryBuilder> sqlExecutor, String table) throws SQLException {
        this(sqlExecutor, table, true);
    }

    public void saveConfig(String key, Path config) throws IOException, SQLException {
        byte[] bytes = Files.readAllBytes(config);
        saveConfig(key, bytes);
    }

    public void saveConfig(String key, byte[] bytes) throws SQLException{
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] base64 = encoder.encode(bytes);
        String base64String = new String(base64);
        sqlExecutor.executeUpdate(
                new SQLQueryBuilder(String.format("INSERT INTO `%s` (content, file) VALUES (?, ?) ON DUPLICATE KEY UPDATE content=?;", table))
                        .setParameter(1, base64String)
                        .setParameter(2, key)
                        .setParameter(3, base64String)
        );
    }

    @Nullable
    public byte[] loadConfig(String key) throws SQLException {
        RowSet rowSet = sqlExecutor.executeQuery(
                new SQLQueryBuilder(String.format("SELECT * FROM `%s` WHERE file = ?;", table))
                        .setParameter(1, key)
        );
        if (!rowSet.next()) return null;
        String content = rowSet.getString("content");
        return Base64.getDecoder().decode(content);
    }
}
