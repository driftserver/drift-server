package io.drift.core.store.serialization;

import io.drift.core.api.Model;

abstract public class Serializer {
	
	private String format;
	
	protected Serializer(String format) {
		super();
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	abstract public Model loadModel(String content, Class<? extends Model> modelClass) throws ModelSerializationException;

	abstract public String from(Model model) throws ModelSerializationException;
	
}
