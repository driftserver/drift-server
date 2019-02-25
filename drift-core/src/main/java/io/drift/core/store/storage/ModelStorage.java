package io.drift.core.store.storage;

abstract public class ModelStorage {

	abstract public void writeContent(StorageId storageId, String model) throws ModelStorageException;

	abstract public String readContent(StorageId storageId) throws ModelStorageException;

	abstract public void writeMetaContent(StorageId storageId, String metaContent) throws ModelStorageException;

	abstract public String readMetaContent(StorageId storageId) throws ModelStorageException;

}
