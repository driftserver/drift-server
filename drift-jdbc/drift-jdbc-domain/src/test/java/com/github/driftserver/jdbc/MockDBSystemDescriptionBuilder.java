package com.github.driftserver.jdbc;

import com.github.driftserver.core.system.*;
import com.github.driftserver.jdbc.domain.system.JDBCConnectionDetails;

public class MockDBSystemDescriptionBuilder {

    public SystemDescription createDummy() {

        SystemDescription systemDescription = new SystemDescription();

        Environment localEnv = new Environment();
        localEnv.setKey(new EnvironmentKey("LOCAL"));
        localEnv.setName("Local");
        systemDescription.getEnvironments().add(localEnv);

        Environment devEnv = new Environment();
        devEnv.setKey(new EnvironmentKey("DEV"));
        devEnv.setName("Develop");
        systemDescription.getEnvironments().add(devEnv);

        SubSystem db = new SubSystem();
        db.setType("jdbc");
        db.setName("database");
        db.setKey(new SubSystemKey("db"));
        systemDescription.getSubSystems().add(db);

        JDBCConnectionDetails connectionDetails = new JDBCConnectionDetails();
        connectionDetails.setJdbcUrl("jdbc://url");
        connectionDetails.setUserName("user1");
        connectionDetails.setPassword("examplepassword");
        connectionDetails.setTableNames(new String[] {"OWNERS", "PETS", "VETS"});

        systemDescription.addConnectionDetails(db.getKey(), localEnv.getKey(), connectionDetails);

        return systemDescription;

    }


}
