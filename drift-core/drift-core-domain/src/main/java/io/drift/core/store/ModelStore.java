package io.drift.core.store;

import io.drift.core.api.FocalArea;
import io.drift.core.store.metadata.MetaModelCreator;
import io.drift.core.store.metadata.MetaModelManager;
import io.drift.core.store.serialization.SerializationManager;
import io.drift.core.store.serialization.Serializer;
import io.drift.core.store.storage.ModelStorageManager;
import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StoragePath;

public class ModelStore {

	private FocalArea focalArea;

	private MetaModelManager metaModelCreation = new MetaModelManager();

	private SerializationManager serialization = new SerializationManager();

	private ModelStorageManager storage = new ModelStorageManager();

	public MetaModelManager getMetaDataManager() {
		return metaModelCreation;
	}

	public MetaModel getMetaModel(StoragePath storageId) throws ModelStoreException {
		Class<MetaModel> metaModelClass = MetaModel.class;
		Serializer metamodelSerializer = serialization.forClass(metaModelClass);
		String metaContent = storage.forId(storageId).readMetaContent(storageId);
		return (MetaModel) metamodelSerializer.loadModel(metaContent, metaModelClass);
	}

	public ModelStorageManager getModelStorageManager() {
		return storage;
	}

	public SerializationManager getSerializationManager() {
		return serialization;
	}

	@SuppressWarnings("unchecked")
	public <STORABLE extends Storable> STORABLE load(StoragePath storageId, Class<STORABLE> modelClass)
			throws ModelStoreException {
		Serializer modelSerializer = serialization.forClass(modelClass);
		String content = storage.forId(storageId).readContent(storageId);
		return (STORABLE) modelSerializer.loadModel(content, modelClass);
	}

	public <STORABLE extends Storable> void save(STORABLE model, StoragePath storageId) throws ModelStoreException {
		@SuppressWarnings("unchecked")
		Class<STORABLE> modelClass = (Class<STORABLE>) model.getClass();

		Serializer modelSerializer = serialization.forClass(modelClass);
		String content = modelSerializer.from(model);
		storage.forId(storageId).writeContent(storageId, content);

		MetaModelCreator<STORABLE> metaModelCreator = metaModelCreation.forModelClass(modelClass);
		if (metaModelCreator != null) {
			MetaModel metaModel = metaModelCreator.createFrom(model);
			Serializer metaDataSerializer = serialization.forClass(metaModel.getClass());
			String metaContent = metaDataSerializer.from(metaModel);
			storage.forId(storageId).writeMetaContent(storageId, metaContent);
		}
	}

	public void setFocalArea(FocalArea focalArea) {
		this.focalArea = focalArea;
	}
}
