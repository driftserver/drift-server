package com.github.driftserver.core.metamodel;

public class ModelDescriptor{

    private Class<? extends Model> modelClass;

    private ModelFormat modelFormat;

    public Class<? extends Model> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<? extends Model> modelClass) {
        this.modelClass = modelClass;
    }

    public ModelFormat getModelFormat() {
        return modelFormat;
    }

    public void setModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
    }
}
