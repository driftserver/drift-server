package io.drift.jdbc.infra;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.drift.jdbc.domain.data.DBDelta;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;

public class DriftJDBCJacksonModule extends SimpleModule {


    public DriftJDBCJacksonModule() {
        super();
        registerSubtypes(
                DBSnapShot.class,
                DBMetaData.class,
                DBDelta.class,
                JDBCConnectionDetails.class
        );


    }

}
