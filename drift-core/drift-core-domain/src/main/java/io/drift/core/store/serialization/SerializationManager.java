package io.drift.core.store.serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializationManager {

	private Map<String, Serializer> serializerMap = new HashMap<>();

	private Serializer defaultSerializer;

	public void registerSerializer(Serializer modelSerializer) {
		serializerMap.put(modelSerializer.getFormat(), modelSerializer);
		defaultSerializer = modelSerializer;
	}

	public Serializer forClass(Class<?> _class) {
		return defaultSerializer;
	}

}
