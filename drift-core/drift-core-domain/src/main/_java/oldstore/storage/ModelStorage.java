package io.drift.core.oldstore.storage;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;

public interface ModelStorage {

	void writeContent(ModelId storageId, String model, ModelURN storagePath, String format) throws ModelStorageException;

	String readContent(ModelId storageId, ModelURN storagePath, String format) throws ModelStorageException;

}
