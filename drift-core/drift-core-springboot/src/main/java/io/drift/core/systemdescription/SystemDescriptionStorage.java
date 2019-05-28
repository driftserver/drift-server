package io.drift.core.systemdescription;

import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import io.drift.core.system.SystemDescription;
import org.springframework.stereotype.Component;

@Component
public class SystemDescriptionStorage {

    private static final StorageId SYSTEM_DESCRIPTION_STORAGE_ID = new StorageId("systemdescription.json");

    private static final StoragePath SYSTEM_STORAGE_PATH = StoragePath.of(new StorageId("system"));

    private final ModelStore modelStore;

    public SystemDescriptionStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
    }

    public SystemDescription load() {
        try {
            SystemDescription systemDescription = modelStore.load(SYSTEM_STORAGE_PATH, SYSTEM_DESCRIPTION_STORAGE_ID, SystemDescription.class);
            return systemDescription;
        } catch (ModelStoreException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
