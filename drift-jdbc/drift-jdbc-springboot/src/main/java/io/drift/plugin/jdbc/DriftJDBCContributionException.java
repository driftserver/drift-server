package io.drift.plugin.jdbc;

import io.drift.core.recording.DriftException;

public class DriftJDBCContributionException extends DriftException {

    public DriftJDBCContributionException(DriftJDBCContributionExceptionType type, Throwable cause) {
        super(type.getCode(), type.getDescription() , cause);
    }
}
