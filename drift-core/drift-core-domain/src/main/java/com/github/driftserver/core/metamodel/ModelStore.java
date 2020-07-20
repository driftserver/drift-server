package com.github.driftserver.core.metamodel;


import com.github.driftserver.core.metamodel.serialization.ModelSerializer;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.metamodel.urn.ModelURNResolver;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ModelStore {

    private ModelURNResolver urnResolver;

    private Map<ModelFormat, ModelSerializer> serializers;

    private Map<Class<? extends Model>, ModelDescriptor> descriptors;

    public ModelStore(ModelURNResolver urnResolver, Map<ModelFormat, ModelSerializer> serializers, Map<Class<? extends Model>, ModelDescriptor> descriptors) {
        this.urnResolver = urnResolver;
        this.serializers = serializers;
        this.descriptors = descriptors;
    }

    public <T extends Model> T read(ModelURN urn, Class<T> modelClass) throws ModelException {

        ModelDescriptor descriptor = descriptors.get(modelClass);
        ModelSerializer serializer = serializers.get(descriptor.getFormat());

        try(Reader reader = urnResolver.getReader(urn, descriptor)) {
            return (T) serializer.read(reader, descriptor);
        } catch(IOException e) {
            throw new ModelException(String.format("error reading model. urn=%s, modelClass=%s", urn, modelClass), e);
        }
    }

    public void write(Model model, ModelURN urn) throws ModelException {

        ModelDescriptor descriptor = descriptors.get(model.getClass());

        ModelSerializer serializer = serializers.get(descriptor.getFormat());

        try(Writer writer = urnResolver.getWriter(urn, descriptor)) {
            serializer.write(writer, model, descriptor);
        } catch(IOException e) {
            throw new ModelException(String.format("error reading model. urn=%s, modelClass=%s", urn, model), e);
        }
    }

    public void shutDown() {
    }


    public static class Builder {

        private ModelURNResolver urnResolver;

        private Map<ModelFormat, ModelSerializer> serializers = new HashMap<>();

        private Map<Class<? extends Model>, ModelDescriptor> descriptors = new HashMap<>();

        public Builder withURNResolver(ModelURNResolver urnResolver) {
            this.urnResolver = urnResolver;
            return this;
        }

        public Builder withSerializer(ModelSerializer serializer) {
            serializers.put(serializer.getFormat(), serializer);
            return this;
        }

        public Builder withModelDescriptor(ModelDescriptor modelDescriptor) {
            descriptors.put(modelDescriptor.getModelClass(), modelDescriptor);
            return this;
        }

        public ModelStore build() {
            return new ModelStore(urnResolver, serializers, descriptors);
        }

    }

    public static Builder builder() { return new Builder(); }

}
