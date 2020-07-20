package com.github.driftserver.core.metamodel;

import com.github.driftserver.core.metamodel.id.ModelId;

abstract public class ModelDescriptor<T extends Model> {

    public abstract Class<T> getModelClass();

    public abstract ModelFormat getFormat();

    public abstract ModelId getModelId(T model);

}
