package io.drift.core.oldstore.storage;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;
import io.drift.core.oldstore.MetaData;
import io.drift.core.oldstore.ModelStoreException;

import java.util.List;

public interface MetaDataStorage {

    void writeMetaData(MetaData metaData) throws ModelStoreException;

    MetaData readMetaData(ModelId storageId) throws ModelStoreException;

    List<MetaData> forParentPath(ModelURN parentPath) throws ModelStoreException;

    void shutDown();
}
