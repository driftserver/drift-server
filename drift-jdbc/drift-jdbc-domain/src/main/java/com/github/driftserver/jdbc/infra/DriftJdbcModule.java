package com.github.driftserver.jdbc.infra;

import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.Module;

public class DriftJdbcModule extends Module {

    @Override
    public void register(ModelStore.Builder builder) {
        builder
            .withJacksonModule(new DriftJDBCJacksonModule());

    }
}
