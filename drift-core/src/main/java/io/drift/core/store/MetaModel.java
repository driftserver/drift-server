package io.drift.core.store;

import io.drift.core.api.Model;

public class MetaModel implements Model {

	private String modelType;

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

}
