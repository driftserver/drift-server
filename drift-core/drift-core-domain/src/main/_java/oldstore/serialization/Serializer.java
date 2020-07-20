package io.drift.core.oldstore.serialization;

import io.drift.core.oldstore.storage.Storable;

abstract public class Serializer {

	private String format;

	protected Serializer(String format) {
		super();
		this.format = format;
	}

	abstract public String from(Storable storable) throws StorableSerializationException;

	public String getFormat() {
		return format;
	}

	abstract public Storable loadModel(String content, Class<? extends Storable> modelClass)
			throws StorableSerializationException;

}
