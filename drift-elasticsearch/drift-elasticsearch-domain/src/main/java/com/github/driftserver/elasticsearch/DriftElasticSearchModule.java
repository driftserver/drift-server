package com.github.driftserver.elasticsearch;

import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.Module;

public class DriftElasticSearchModule extends Module {

    @Override
    public void register(ModelStore.Builder builder) {
        builder.withJacksonModule(new DriftElasticSearchJacksonModule());
    }

}
