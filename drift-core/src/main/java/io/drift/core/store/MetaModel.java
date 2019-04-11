package io.drift.core.store;

import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StorageId;

public class MetaModel implements Storable {

	private StorageId id;

	private String modelType;

	@Override
	public StorageId getId() {
		return id;
	}

	public String getModelType() {
		return modelType;
	}

	public void setId(StorageId id) {
		this.id = id;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

}
