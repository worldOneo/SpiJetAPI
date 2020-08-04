package de.worldOneo.spiJetAPI.sql;

import com.zaxxer.hikari.HikariDataSource;
import de.worldOneo.spiJetAPI.utils.SpiJetBuilder;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Setter
@Accessors(chain = true)
public class DataSourceBuilder implements SpiJetBuilder<HikariDataSource> {
    private String username;
    private String password;
    private String jdbcUrl;
    private String dataSourceClassName;
    private int poolSize = 3;
    private boolean autoCommit = true;
    private long connectionTimeout = 30000;
    private long idleTimeout = 600000;

    @Setter(AccessLevel.NONE)
    private Map<String, String> customProperties = new HashMap<>();


    public DataSourceBuilder addProperty(String key, String value) {
        customProperties.put(key, value);
        return this;
    }

    public HikariDataSource build() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        if (username != null) {
            System.out.println("Username: " + username);
            hikariDataSource.setUsername(username);
        }
        if (password != null) {
            System.out.println("Password: " + password);
            hikariDataSource.setPassword(password);
        }
        if (jdbcUrl != null) {
            System.out.println("JdbcURL: " + jdbcUrl);
            hikariDataSource.setJdbcUrl(jdbcUrl);
        }
        if (dataSourceClassName != null) {
            System.out.println("DataClass: " + dataSourceClassName);
            hikariDataSource.setDataSourceClassName(dataSourceClassName);
        }
        hikariDataSource.setMaximumPoolSize(poolSize);
        hikariDataSource.setAutoCommit(autoCommit);
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setIdleTimeout(idleTimeout);
        for (Map.Entry<String, String> entry : customProperties.entrySet()) {
            hikariDataSource.addDataSourceProperty(entry.getKey(), entry.getValue());
        }
        return hikariDataSource;
    }
}
