package com.github.driftserver.core.metamodel.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.driftserver.core.metamodel.ModelFormat;

public class JsonModelSerializer extends AbstractJacksonModelSerializer {

	public JsonModelSerializer() {
		super(ModelFormat.JSON);
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

}
