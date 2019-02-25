package io.drift.core.store;

import io.drift.core.api.Model;
import io.drift.core.store.metadata.MetaModelManager;
import io.drift.core.store.serialization.SerializationManager;
import io.drift.core.store.serialization.Serializer;
import io.drift.core.store.storage.ModelStorageManager;
import io.drift.core.store.storage.StorageId;

public class ModelStore {

	private SerializationManager serialization = new SerializationManager();

	private ModelStorageManager storage = new ModelStorageManager();

	private MetaModelManager metaModelCreation = new MetaModelManager();

	public SerializationManager getSerializationManager() {
		return serialization;
	}

	public ModelStorageManager getModelStorageManager() {
		return storage;
	}

	public MetaModelManager getMetaDataManager() {
		return metaModelCreation;
	}

	public <M extends Model> void save(M model, StorageId storageId) throws ModelStoreException {
		@SuppressWarnings("unchecked")
		Class<M> modelClass = (Class<M>) model.getClass();

		Serializer modelSerializer = serialization.forClass(modelClass);
		String content = modelSerializer.from(model);
		storage.forId(storageId).writeContent(storageId, content);

		MetaModel metaModel = metaModelCreation.forModelClass(modelClass).createFrom(model);
		Serializer metaDataSerializer = serialization.forClass(metaModel.getClass());
		String metaContent = metaDataSerializer.from(metaModel);
		storage.forId(storageId).writeMetaContent(storageId, metaContent);
	}

	@SuppressWarnings("unchecked")
	public <M extends Model> M load(StorageId storageId, Class<M> modelClass) throws ModelStoreException {
		Serializer modelSerializer = serialization.forClass(modelClass);
		String content = storage.forId(storageId).readContent(storageId);
		return (M) modelSerializer.loadModel(content, modelClass);
	}

	public MetaModel getMetaModel(StorageId storageId) throws ModelStoreException {
		Class<MetaModel> metaModelClass = MetaModel.class;
		Serializer metamodelSerializer = serialization.forClass(metaModelClass);
		String metaContent = storage.forId(storageId).readMetaContent(storageId);
		return (MetaModel) metamodelSerializer.loadModel(metaContent, metaModelClass);
	}
}
