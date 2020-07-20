package com.github.driftserver.jdbc;

import com.github.driftserver.core.infra.logging.DriftException;

public class DriftJDBCContributionException extends DriftException {

    public DriftJDBCContributionException(DriftJDBCContributionExceptionType type, Throwable cause) {
        super(type.getCode(), type.getDescription() , cause);
    }
}
