package io.drift.core.store;

import io.drift.core.store.serialization.SerializationManager;
import io.drift.core.store.serialization.Serializer;
import io.drift.core.store.storage.*;

import java.util.List;

public class ModelStore {

	private SerializationManager serialization = new SerializationManager();

	private ModelStorageManager storage = new ModelStorageManager();

	private MetaDataStorage metaDataStorage;

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
		String format = serialization.formatForClass(modelClass);
		Serializer modelSerializer = serialization.forFormat(format);
		String content = storage.forPath(path).readContent(storageId, path, format);
		return (STORABLE) modelSerializer.loadModel(content, modelClass);
	}

	public List<MetaData> list(StoragePath path)  throws ModelStoreException {
		return metaDataStorage.forParentPath(path);
	}

	public <STORABLE extends Storable> void save(STORABLE model, MetaData metaData) throws ModelStoreException {
		@SuppressWarnings("unchecked")
		Class<STORABLE> modelClass = (Class<STORABLE>) model.getClass();
		String format = serialization.formatForClass(modelClass);
		Serializer modelSerializer = serialization.forFormat(format);
		String content = modelSerializer.from(model);

		storage.forPath(metaData.getPath()).writeContent(metaData.getStorageId(), content, metaData.getPath(), format);

		metaDataStorage.writeMetaData(metaData);

	}

	public MetaData getMetaData(StorageId storageId) throws ModelStoreException {
		return metaDataStorage.readMetaData(storageId);
	}

	public ModelStore withMetaDataStorage(MetaDataStorage metaDataStorage) {
		this.metaDataStorage = metaDataStorage;
		return this;
	}

	public ModelStore withSerializer(Serializer serializer) {
		getSerializationManager().registerSerializer(serializer);
		return this;
	}

	public ModelStore withModelStorage(ModelStorage modelStorage) {
		getModelStorageManager().registerStorage(modelStorage);
		return this;
	}

	public ModelStore withDefaultFormat(String format) {
		getSerializationManager().setDefaultFormat(format);
		return this;
	}

	public ModelStore withFormatForClass(Class<?> _class, String format) {
		getSerializationManager().setFormatForForClass(format, _class);
		return this;
	}

	public void shutDown() {
		metaDataStorage.shutDown();
	}
}
