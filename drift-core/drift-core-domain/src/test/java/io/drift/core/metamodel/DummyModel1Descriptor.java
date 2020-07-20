package io.drift.core.metamodel;

import io.drift.core.metamodel.id.ModelId;

public class DummyModel1Descriptor extends ModelDescriptor<DummyModel1> {

    @Override
    public Class<DummyModel1> getModelClass() {
        return DummyModel1.class;
    }

    @Override
    public ModelFormat getFormat() {
        return ModelFormat.JSON;
    }

    @Override
    public ModelId getModelId(DummyModel1 model) {
        return new ModelId(model.getAtt1());
    }
}
