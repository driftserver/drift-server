package io.drift.core.oldstore.storage;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;
import io.drift.core.oldstore.MetaData;
import io.drift.core.oldstore.ModelStoreException;

import java.nio.file.Path;
import java.util.List;

public class FileSystemMetaDataStorage implements MetaDataStorage {

    private Path baseDir;

    public FileSystemMetaDataStorage(Path baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void writeMetaData(MetaData metaData) throws ModelStoreException {

    }

    @Override
    public MetaData readMetaData(ModelId storageId) throws ModelStoreException {
        return null;
    }

    @Override
    public List<MetaData> forParentPath(ModelURN parentPath) throws ModelStoreException {
        return null;
    }

    @Override
    public void shutDown() {

    }
}
