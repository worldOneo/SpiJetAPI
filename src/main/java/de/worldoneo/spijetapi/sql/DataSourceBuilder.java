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
        this.customProperties.put(key, value);
        return this;
    }

    public HikariDataSource build() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setMaximumPoolSize(this.poolSize);
        if (this.username != null) hikariDataSource.setUsername(this.username);
        if (this.password != null) hikariDataSource.setPassword(this.password);
        if (this.jdbcUrl != null) hikariDataSource.setJdbcUrl(this.jdbcUrl);
        if (this.dataSourceClassName != null) hikariDataSource.setDataSourceClassName(this.dataSourceClassName);
        if (this.driverClassName != null) hikariDataSource.setDriverClassName(this.driverClassName);
        hikariDataSource.setAutoCommit(this.autoCommit);
        hikariDataSource.setConnectionTimeout(this.connectionTimeout);
        hikariDataSource.setIdleTimeout(this.idleTimeout);
        for (Map.Entry<String, Object> entry : this.customProperties.entrySet()) {
            hikariDataSource.addDataSourceProperty(entry.getKey(), entry.getValue());
        }
        return hikariDataSource;
    }
}
