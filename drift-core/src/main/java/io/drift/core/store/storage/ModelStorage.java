package io.drift.core.store.storage;

abstract public class ModelStorage {

	abstract public void writeContent(StoragePath storageId, String model) throws ModelStorageException;

	abstract public String readContent(StoragePath storageId) throws ModelStorageException;

	abstract public void writeMetaContent(StoragePath storageId, String metaContent) throws ModelStorageException;

	abstract public String readMetaContent(StoragePath storageId) throws ModelStorageException;

}
