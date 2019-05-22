package io.drift.core.systemdescription;

import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import io.drift.core.system.SystemDescription;
import org.springframework.stereotype.Component;

@Component
public class SystemDescriptionStorage {

    private static final StorageId STORAGE_ID_SYSTEM_DESCRIPTION = new StorageId("systemdescription.json");

    private final ModelStore modelStore;

    public SystemDescriptionStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
    }

    private StoragePath resolveSystemDescriptionPath() {
        return StoragePath.of(STORAGE_ID_SYSTEM_DESCRIPTION);
    }

    public SystemDescription load() {
        try {
            SystemDescription systemDescription = modelStore.load(resolveSystemDescriptionPath(), SystemDescription.class);
            return systemDescription;
        } catch (ModelStoreException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
