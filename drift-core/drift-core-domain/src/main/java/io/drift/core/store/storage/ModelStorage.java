package io.drift.core.store.storage;

public interface ModelStorage {

	void writeContent(StorageId storageId, String model, StoragePath storagePath, String format) throws ModelStorageException;

	String readContent(StorageId storageId, StoragePath storagePath, String format) throws ModelStorageException;

}
