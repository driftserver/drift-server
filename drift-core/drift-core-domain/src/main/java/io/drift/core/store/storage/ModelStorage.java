package io.drift.core.store.storage;

abstract public class ModelStorage {

	abstract public void writeContent(StoragePath storagePath, String model) throws ModelStorageException;

	abstract public String readContent(StoragePath storagePath) throws ModelStorageException;

	abstract public void writeMetaContent(StoragePath storagePath, String metaContent) throws ModelStorageException;

	abstract public String readMetaContent(StoragePath storagePath) throws ModelStorageException;

}
