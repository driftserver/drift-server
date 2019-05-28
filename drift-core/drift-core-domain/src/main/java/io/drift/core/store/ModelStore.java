package io.drift.core.store;

import io.drift.core.store.serialization.SerializationManager;
import io.drift.core.store.serialization.Serializer;
import io.drift.core.store.storage.*;

import java.util.List;

public class ModelStore {

	private static SerializationManager serialization = new SerializationManager();

	private static ModelStorageManager storage = new ModelStorageManager();

	private static MetaDataStorage metaDataStorage = new LuceneMetaDataStorage();

	public ModelStorageManager getModelStorageManager() {
		return storage;
	}

	public SerializationManager getSerializationManager() {
		return serialization;
	}


	@SuppressWarnings("unchecked")
	public <STORABLE extends Storable> STORABLE load(StorageId storageId, Class<STORABLE> modelClass)
			throws ModelStoreException {
		MetaData metaData = metaDataStorage.readMetaData(storageId);
		return (STORABLE) load(metaData.getPath(), storageId, modelClass);
	}

	@SuppressWarnings("unchecked")
	public <STORABLE extends Storable> STORABLE load(StoragePath path, StorageId storageId, Class<STORABLE> modelClass)
			throws ModelStoreException {
		Serializer modelSerializer = serialization.forClass(modelClass);
		String content = storage.forPath(path).readContent(path, storageId);
		return (STORABLE) modelSerializer.loadModel(content, modelClass);
	}

	public List<MetaData> list(StoragePath path)  throws ModelStoreException {
		return metaDataStorage.forParentPath(path);
	}

	public <STORABLE extends Storable> void save(STORABLE model, MetaData metaData) throws ModelStoreException {
		@SuppressWarnings("unchecked")
		Class<STORABLE> modelClass = (Class<STORABLE>) model.getClass();
		Serializer modelSerializer = serialization.forClass(modelClass);
		String content = modelSerializer.from(model);

		storage.forPath(metaData.getPath()).writeContent(metaData.getPath(), metaData.getStorageId(), content);

		metaDataStorage.writeMetaData(metaData);

	}

	public MetaData getMetaData(StorageId storageId) throws ModelStoreException {
		return metaDataStorage.readMetaData(storageId);
	}
}
