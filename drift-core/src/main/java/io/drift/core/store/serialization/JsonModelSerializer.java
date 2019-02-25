package io.drift.core.store.serialization;

import io.drift.core.api.Model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonModelSerializer extends Serializer {

	static public String FORMAT = "json";
	
	private ObjectMapper objectMapper;
	
	public JsonModelSerializer() {
		super(FORMAT);
		initmapper();
	}
	
	private void initmapper() {
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public Model loadModel(String content, Class<? extends Model> modelClass) throws ModelSerializationException {
		try {
			return (Model) objectMapper.readValue(content, modelClass);
		} catch (IOException e) {
			throw new ModelSerializationException(e);
		} 
	}

	@Override
	public String from(Model model) throws ModelSerializationException {
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
		} catch (JsonProcessingException e) {
			throw new ModelSerializationException(e);
		}
	}

}
