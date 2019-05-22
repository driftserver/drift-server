package io.drift.core.recording;

import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import org.springframework.stereotype.Component;

@Component
public class RecordingStorage {

    private static final StorageId STORAGE_ID_RECORDINGS = new StorageId("recordings");

    private final ModelStore modelStore;

    public RecordingStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
    }

    private StoragePath resolveRecordingPath(RecordingId recordingId) {
        return resolveRecordingsPath().resolve(recordingId);
    }

    private StoragePath resolveRecordingsPath() {
        return StoragePath.of(STORAGE_ID_RECORDINGS);
    }


    public Recording load(RecordingId recordingId) {
        try {
            return modelStore.load(resolveRecordingPath(recordingId), Recording.class);
        } catch (ModelStoreException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }
    }

    public void store(Recording recording) {
        try {
            modelStore.save(recording, resolveRecordingPath(recording.getId()));
        } catch (ModelStoreException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }

    }

}
