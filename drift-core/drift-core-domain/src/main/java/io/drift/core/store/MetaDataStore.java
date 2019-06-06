package io.drift.core.store;

public class MetaDataStore {
/*
    private final MetaDataStorage metaDataStorage;
    private final

    public MetaDataStore(MetaDataStorage metaDataStorage) {
        this.metaDataStorage = metaDataStorage;
    }

    public MetaModelManager getMetaDataManager() {
        return metaModelCreation;
    }

    public MetaModel getMetaModel(StoragePath storageId) throws ModelStoreException {
        Class<MetaModel> metaModelClass = MetaModel.class;
        Serializer metamodelSerializer = serialization.forClass(metaModelClass);
        String metaContent = storage.forPath(storageId).readMetaContent(storageId);
        return (MetaModel) metamodelSerializer.loadModel(metaContent, metaModelClass);
    }

    public void store() {
        MetaModelCreator<STORABLE> metaModelCreator = metaModelCreation.forModelClass(modelClass);
        if (metaModelCreator != null) {
            MetaModel metaModel = metaModelCreator.createFrom(model);
            Serializer metaDataSerializer = serialization.forClass(metaModel.getClass());
            String metaContent = metaDataSerializer.from(metaModel);
            storage.forPath(storageId).writeMetaContent(storageId, metaContent);
        }
    }
*/

}
