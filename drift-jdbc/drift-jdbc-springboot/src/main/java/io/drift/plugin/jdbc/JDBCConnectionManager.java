package io.drift.plugin.jdbc;

import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.h2.jdbcx.JdbcDataSource;
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
        if (dataSource==null) {
            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setURL(connectionDetails.getJdbcUrl());
            jdbcDataSource.setUser(connectionDetails.getUserName());
            jdbcDataSource.setPassword(connectionDetails.getPassword());
            dataSource = jdbcDataSource;
            dataSources.put(key, dataSource);
        }
        return dataSource;

    }


}
