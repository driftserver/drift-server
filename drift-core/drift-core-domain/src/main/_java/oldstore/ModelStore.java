package io.drift.core.oldstore;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;
import io.drift.core.oldstore.serialization.SerializerRegistry;
import io.drift.core.oldstore.serialization.Serializer;
import io.drift.core.oldstore.storage.*;
import io.drift.core.system.SystemDescription;

import java.util.List;

public class ModelStore {

	private SerializerRegistry serializerRegistry = new SerializerRegistry();

	private MetaDataStorage metaDataStorage;

	private ModelStorage modelStorage;


	@SuppressWarnings("unchecked")
	public <STORABLE extends Storable> STORABLE load(ModelId storageId, Class<STORABLE> modelClass)
			throws ModelStoreException {
		MetaData metaData = metaDataStorage.readMetaData(storageId);
		return load(metaData.getPath(), storageId, modelClass);
	}

	@SuppressWarnings("unchecked")
	public <STORABLE extends Storable> STORABLE load(ModelURN path, ModelId storageId, Class<STORABLE> modelClass)
			throws ModelStoreException {
		String format = serializerRegistry.formatForClass(modelClass);
		Serializer modelSerializer = serializerRegistry.forFormat(format);
		String content = modelStorage.readContent(storageId, path, format);
		return (STORABLE) modelSerializer.loadModel(content, modelClass);
	}

	public List<MetaData> list(ModelURN path)  throws ModelStoreException {
		return metaDataStorage.forParentPath(path);
	}

	public <STORABLE extends Storable> void save(STORABLE model, MetaData metaData) throws ModelStoreException {
		@SuppressWarnings("unchecked")
		Class<STORABLE> modelClass = (Class<STORABLE>) model.getClass();
		String format = serializerRegistry.formatForClass(modelClass);
		Serializer modelSerializer = serializerRegistry.forFormat(format);
		String content = modelSerializer.from(model);

		modelStorage.writeContent(metaData.getStorageId(), content, metaData.getPath(), format);

		metaDataStorage.writeMetaData(metaData);

	}

	public MetaData getMetaData(ModelId storageId) throws ModelStoreException {
		return metaDataStorage.readMetaData(storageId);
	}

	public void shutDown() {
		metaDataStorage.shutDown();
	}

	public ModelStore withSerializer(Serializer serializer) {
		return this;
	}

	public ModelStore withDefaultFormat(String jsonFormat) {
		return this;
	}

	public ModelStore withFormatForClass(Class<SystemDescription> systemDescriptionClass, String yamlFormat) {
		return this;
	}

	public ModelStore withModelStorage(FileSystemModelStorage fileSystemModelStorage) {
		return this;
	}

	public ModelStore withMetaDataStorage(FileSystemMetaDataStorage fileSystemMetaDataStorage) {
		return this;
	}
}
