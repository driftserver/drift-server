package io.drift.core.store.storage;


public class ModelStorageManager {

//	private Map<StorageId, ModelStorage> storageMap = new HashMap<StorageId, ModelStorage>();
	
	private ModelStorage defaultStorage;
	
	public void registerStorage(ModelStorage modelStorage) {
		defaultStorage = modelStorage;
	}
	
	public ModelStorage forPath(StoragePath storageId) {
		return defaultStorage;
	}

}
