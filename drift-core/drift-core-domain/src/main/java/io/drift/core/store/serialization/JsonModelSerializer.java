package io.drift.core.store.serialization;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import io.drift.core.store.storage.Storable;

public class JsonModelSerializer extends Serializer {

	static public String FORMAT = "json";

	private ObjectMapper objectMapper;

	public JsonModelSerializer() {
		super(FORMAT);
		initmapper();
	}

	@Override
	public String from(Storable storable) throws StorableSerializationException {
		try {
			return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(storable);
		} catch (JsonProcessingException e) {
			throw new StorableSerializationException(e);
		}
	}

	private void initmapper() {
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
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
