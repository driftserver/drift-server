package com.github.driftserver.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.github.driftserver.jdbc.domain.system.JDBCConnectionDetails;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class JDBCConnectionManager {

    private Map<String, DataSource> dataSources = new HashMap<>();

    public DataSource getDataSource(JDBCConnectionDetails connectionDetails) {
        String key = connectionDetails.getJdbcUrl();
        DataSource dataSource = dataSources.get(key);
        if (dataSource == null) {

            HikariConfig config = new HikariConfig();
            HikariDataSource hikariDataSource;

            config.setJdbcUrl(connectionDetails.getJdbcUrl());
            config.setUsername(connectionDetails.getUserName());
            config.setPassword(connectionDetails.getPassword());
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            hikariDataSource = new HikariDataSource(config);


            dataSource = hikariDataSource;
            dataSources.put(key, dataSource);
        }
        return dataSource;

    }

    public void stopDataSource(JDBCConnectionDetails connectionDetails) {
        String key = connectionDetails.getJdbcUrl();
        HikariDataSource dataSource = (HikariDataSource)dataSources.get(key);
        if (dataSource != null) {
            dataSource.close();
            dataSources.remove(key);
        }
    }

}
