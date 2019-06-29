package io.drift.plugin.jdbc;

public enum DriftJDBCContributionExceptionType {

    CONNECTION_POOL_INIT_ERROR("DRIFT-JDBC-001", "Error initializing JDBC Connection pool");

    private String code;

    private String description;

    DriftJDBCContributionExceptionType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
