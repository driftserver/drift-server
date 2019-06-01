package io.drift.core.store.serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializationManager {

	private Map<String, Serializer> serializerForFormat = new HashMap<>();

	private Map<Class<?>, String> formatForClass = new HashMap<>();

	private String defaultFormat;

	public void registerSerializer(Serializer serializer) {
		serializerForFormat.put(serializer.getFormat(), serializer);
	}

	public Serializer forClass(Class<?> _class) {
		String format = formatForClass.get(_class);
		format = format == null ? defaultFormat : format;
		return serializerForFormat.get(format);
	}

	public void setDefaultFormat(String format) {
		this.defaultFormat = format;
	}

	public void setFormatForForClass(String format, Class<?> _class) {
		formatForClass.put(_class, format);
	}

}
