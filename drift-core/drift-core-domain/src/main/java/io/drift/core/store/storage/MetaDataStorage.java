package io.drift.core.store.storage;

import io.drift.core.store.MetaData;
import io.drift.core.store.ModelStoreException;

import java.util.List;

public interface MetaDataStorage {

    void writeMetaData(MetaData metaData) throws ModelStoreException;

    MetaData readMetaData(StorageId storageId) throws ModelStoreException;

    List<MetaData> forParentPath(StoragePath parentPath) throws ModelStoreException;

    void shutDown();
}
