package io.drift.core.store.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.drift.core.store.storage.Storable;

import java.io.IOException;

abstract public class AbstractJacksonModelSerializer extends Serializer{

    protected ObjectMapper objectMapper;

    public AbstractJacksonModelSerializer(String format) {
        super(format);
        initMapper();
    }

    protected abstract void initMapper();

    @Override
    public String from(Storable storable) throws StorableSerializationException {
        try {
            return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(storable);
        } catch (JsonProcessingException e) {
            throw new StorableSerializationException(e);
        }
    }

    @Override
    public Storable loadModel(String content, Class<? extends Storable> storableClass)
            throws StorableSerializationException {
        try {
            return objectMapper.readValue(content, storableClass);
        } catch (IOException e) {
            throw new StorableSerializationException(e);
        }
    }

    public void registerModule(Module module) {
        objectMapper.registerModule(module);
    }

}
