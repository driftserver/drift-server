package io.drift.plugin.jdbc;

import io.drift.core.infra.logging.DriftException;

public class DriftJDBCContributionException extends DriftException {

    public DriftJDBCContributionException(DriftJDBCContributionExceptionType type, Throwable cause) {
        super(type.getCode(), type.getDescription() , cause);
    }
}
