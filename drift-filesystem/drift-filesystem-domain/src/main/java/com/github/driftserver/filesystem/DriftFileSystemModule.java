package com.github.driftserver.filesystem;

import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.Module;

public class DriftFileSystemModule extends Module {

    @Override
    public void register(ModelStore.Builder builder) {
        builder.withJacksonModule(new DriftFileSystemJacksonModule());
    }

}
