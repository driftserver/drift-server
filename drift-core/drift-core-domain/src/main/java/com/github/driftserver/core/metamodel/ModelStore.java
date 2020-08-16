package com.github.driftserver.core.metamodel;


import com.github.driftserver.core.metamodel.serialization.AbstractJacksonModelSerializer;
import com.github.driftserver.core.metamodel.serialization.ModelSerializer;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.metamodel.urn.ModelURNResolver;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelStore {

    private ModelURNResolver urnResolver;

    private Map<ModelFormat, ModelSerializer> serializers;

    private Map<Class<? extends Model>, ModelFormat> modelFormats;

    public ModelStore(ModelURNResolver urnResolver, Map<ModelFormat, ModelSerializer> serializers, Map<Class<? extends Model>, ModelFormat> modelFormats) {
        this.urnResolver = urnResolver;
        this.serializers = serializers;
        this.modelFormats = modelFormats;
    }

    public <T extends Model> T read(ModelURN urn, Class<T> modelClass) throws ModelException {

        ModelFormat modelFormat = modelFormats.get(modelClass);
        if (modelFormat==null) {
            throw new ModelException(String.format("no descriptor for %s model", modelClass));
        }
        ModelSerializer serializer = serializers.get(modelFormat);
        if (serializer ==null) {
            throw new ModelException(String.format("no %s serializer registered", modelFormat));
        }

        try(Reader reader = urnResolver.getReader(urn, modelFormat)) {
            return (T) serializer.read(reader, modelClass);
        } catch(IOException e) {
            throw new ModelException(String.format("error reading model. urn=%s, modelClass=%s", urn, modelClass), e);
        }
    }

    public void write(Model model, ModelURN urn) throws ModelException {
        if (model==null) {
            throw new ModelException("model is required");
        }

        Class<? extends Model> modelClass = model.getClass();
        ModelFormat modelFormat = modelFormats.get(modelClass);
        if (modelFormat==null) {
            throw new ModelException(String.format("no descriptor for %s model", modelClass));
        }
        ModelSerializer serializer = serializers.get(modelFormat);
        if (serializer ==null) {
            throw new ModelException(String.format("no %s serializer registered", modelFormat));
        }

        try(Writer writer = urnResolver.getWriter(urn, modelFormat)) {
            serializer.write(writer, model);
        } catch(IOException e) {
            throw new ModelException(String.format("error writing model. urn=%s, modelClass=%s", urn, model), e);
        }
    }

    public static class Builder {

        private ModelURNResolver urnResolver;

        private Map<ModelFormat, ModelSerializer> serializers = new HashMap<>();

        private Map<Class<? extends Model>, ModelFormat> modelFormats = new HashMap<>();

        private List<com.fasterxml.jackson.databind.Module> jacksonModules = new ArrayList<>();

        public Builder withModules(Module... modules) {
            for(Module module: modules) {
                module.register(this);
            }
            return this;
        }

        Builder withURNResolver(ModelURNResolver urnResolver) {
            this.urnResolver = urnResolver;
            return this;
        }

        Builder withSerializer(ModelSerializer serializer) {
            serializers.put(serializer.getFormat(), serializer);
            return this;
        }

        Builder withSerializers(ModelSerializer... serializers) {
            for(ModelSerializer modelSerializer: serializers) {
                withSerializer(modelSerializer);
            }
            return this;
        }

        public Builder withModel(Class<? extends Model> modelClass, ModelFormat format) {
            modelFormats.put(modelClass, format);
            return this;
        }

        public Builder withJacksonModule(com.fasterxml.jackson.databind.Module jacksonModule) {
            jacksonModules.add(jacksonModule);
            return this;
        }

        public ModelStore build() {
            for(ModelSerializer modelSerializer: serializers.values()) {
                if (modelSerializer instanceof AbstractJacksonModelSerializer) {
                    AbstractJacksonModelSerializer jacksonModelSerializer = (AbstractJacksonModelSerializer) modelSerializer;
                    for(com.fasterxml.jackson.databind.Module jacksonModule: jacksonModules) {
                        jacksonModelSerializer.registerJacksonModule(jacksonModule);
                    }
                }
            }
            return new ModelStore(urnResolver, serializers, modelFormats);
        }

    }

    public static Builder builder() { return new Builder(); }

}
