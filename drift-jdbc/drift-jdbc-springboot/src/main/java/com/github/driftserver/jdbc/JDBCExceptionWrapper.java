package com.github.driftserver.jdbc;

import com.zaxxer.hikari.pool.HikariPool;

public class JDBCExceptionWrapper {
    static public Exception wrap(Exception e) {
        if (e instanceof HikariPool.PoolInitializationException) {
            return new DriftJDBCContributionException(DriftJDBCContributionExceptionType.CONNECTION_POOL_INIT_ERROR, e);
        }
        else return e;
    }
}
