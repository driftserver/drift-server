package io.drift.core.oldstore.serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializerRegistry {

	private Map<String, Serializer> serializerForFormat = new HashMap<>();

	private Map<Class<?>, String> formatForClass = new HashMap<>();

	private String defaultFormat;

	public void registerSerializer(Serializer serializer) {
		serializerForFormat.put(serializer.getFormat(), serializer);
	}

	public void setDefaultFormat(String format) {
		this.defaultFormat = format;
	}

	public void setFormatForForClass(String format, Class<?> _class) {
		formatForClass.put(_class, format);
	}

	public String formatForClass(Class<?> modelClass) {
		String format = formatForClass.get(modelClass);
		format = format == null ? defaultFormat : format;
		return format;
	}

	public Serializer forFormat(String format) {
		return serializerForFormat.get(format);
	}

}
