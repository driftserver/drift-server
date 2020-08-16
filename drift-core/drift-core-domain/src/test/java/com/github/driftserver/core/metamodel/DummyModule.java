package com.github.driftserver.core.metamodel;

public class DummyModule extends Module {

    @Override
    public void register(ModelStore.Builder builder) {
        builder.withModel(DummyModel1.class, ModelFormat.JSON);
        builder.withModel(DummyModel2.class, ModelFormat.JSON);
    }

}
