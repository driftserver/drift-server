package com.github.driftserver.jdbc.infra;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.driftserver.jdbc.domain.data.DBDelta;
import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.metadata.DBMetaData;
import com.github.driftserver.jdbc.domain.system.JDBCConnectionDetails;

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
