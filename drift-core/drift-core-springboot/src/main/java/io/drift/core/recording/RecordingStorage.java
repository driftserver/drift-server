package io.drift.core.recording;

import io.drift.core.store.MetaData;
import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecordingStorage {

    private static final StoragePath RECORDINGS_PATH = StoragePath.of(new StorageId("recordings"));

    private final ModelStore modelStore;

    public RecordingStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
    }


    public Recording load(RecordingId recordingId) {
        try {
            return modelStore.load(recordingId , Recording.class);
        } catch (ModelStoreException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }
    }

    public void store(Recording recording) {
        try {
            MetaData metaData = MetaData.builder()
                    .withStorageId(recording.getId())
                    .withPath(RECORDINGS_PATH)
                    .withTimeStamp(LocalDateTime.now())
                    .withDescription(recording.getName())
                    .build();
            modelStore.save(recording, metaData);
        } catch (ModelStoreException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }

    }

    public List<RecordingSummary> list() {
        try {
            return modelStore.list(RECORDINGS_PATH).stream()
                .map(metaData -> new RecordingSummary(new RecordingId(metaData.getStorageId().getId()), metaData.getTimeStamp(), metaData.getDescription()))
                .collect(Collectors.toList());
        } catch (ModelStoreException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
