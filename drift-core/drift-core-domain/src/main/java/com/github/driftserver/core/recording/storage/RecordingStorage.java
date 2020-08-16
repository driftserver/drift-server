package com.github.driftserver.core.recording.storage;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.metadata.MetaData;
import com.github.driftserver.core.metamodel.metadata.MetaDataStorage;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.recording.RecordingSummary;
import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.recording.model.RecordingId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RecordingStorage {

    private static final ModelURN RECORDINGS_PATH = ModelURN.of(new ModelId("recordings"));

    private final ModelStore modelStore;

    private final MetaDataStorage metaDataStorage;
    private RecordingId recordingId;

    public RecordingStorage(ModelStore modelStore, MetaDataStorage metaDataStorage) {
        this.modelStore = modelStore;
        this.metaDataStorage = metaDataStorage;
    }


    public Recording load(RecordingId recordingId) {
        try {
            return modelStore.read(toUrn(recordingId), Recording.class);
        } catch (ModelException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    private ModelURN toUrn(RecordingId recordingId) {
        return RECORDINGS_PATH.resolve(new ModelId(recordingId.getId()));
    }

    public void store(Recording recording) {
        try {
            recordingId = recording.getId();
            ModelURN modelURN = toUrn(recordingId);

            MetaData metaData = MetaData.builder()
                    .withModelId(recordingId)
                    .withModelURN(modelURN)
                    .withTimeStamp(LocalDateTime.now())
                    .withDescription(recording.getName())
                    .build();

            modelStore.write(recording, modelURN);
            metaDataStorage.writeMetaData(modelURN, metaData);
        } catch (ModelException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

    }

    public List<RecordingSummary> list() {
        try {
            return metaDataStorage.listMetaData(RECORDINGS_PATH).stream()
                    .map(metaData -> new RecordingSummary(new RecordingId(metaData.getModelId().getId()), metaData.getTimeStamp(), metaData.getDescription()))
                    .collect(Collectors.toList());
        } catch (ModelException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
