package com.github.driftserver.jdbc.domain.system;

import com.github.driftserver.core.system.SubSystemConnectionDetails;

public class JDBCConnectionDetails implements SubSystemConnectionDetails {

    private String userName;

    private String password;

    private String jdbcUrl;

    private String[] tableNames;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

}
