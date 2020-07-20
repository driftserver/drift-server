package io.drift.core.metamodel;

import io.drift.core.metamodel.id.ModelId;

abstract public class ModelDescriptor<T extends Model> {

    public abstract Class<T> getModelClass();

    public abstract ModelFormat getFormat();

    public abstract ModelId getModelId(T model);

}
