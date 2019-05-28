package io.drift.core.store.storage;

import java.util.List;

public interface ModelStorage {

	public void writeContent(StoragePath storagePath, StorageId storageId, String model) throws ModelStorageException;

	public String readContent(StoragePath storagePath, StorageId storageId) throws ModelStorageException;

}
