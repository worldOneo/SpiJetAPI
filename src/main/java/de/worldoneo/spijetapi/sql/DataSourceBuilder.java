package de.worldoneo.spijetapi.sql;

import com.zaxxer.hikari.HikariDataSource;
import de.worldoneo.spijetapi.utils.SpiJetBuilder;
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
    private String driverClassName;
    private int poolSize = 3;
    private boolean autoCommit = true;
    private long connectionTimeout = 30000;
    private long idleTimeout = 600000;

    @Setter(AccessLevel.NONE)
    private Map<String, Object> customProperties = new HashMap<>();


    public DataSourceBuilder addProperty(String key, Object value) {
        customProperties.put(key, value);
        return this;
    }

    public HikariDataSource build() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setMaximumPoolSize(poolSize);
        if (username != null) hikariDataSource.setUsername(username);
        if (password != null) hikariDataSource.setPassword(password);
        if (jdbcUrl != null) hikariDataSource.setJdbcUrl(jdbcUrl);
        if (dataSourceClassName != null) hikariDataSource.setDataSourceClassName(dataSourceClassName);
        if (driverClassName != null) hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setAutoCommit(autoCommit);
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setIdleTimeout(idleTimeout);
        for (Map.Entry<String, Object> entry : customProperties.entrySet()) {
            hikariDataSource.addDataSourceProperty(entry.getKey(), entry.getValue());
        }
        return hikariDataSource;
    }
}
