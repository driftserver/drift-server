package com.github.driftserver.core.recording.storage;

import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;
import com.github.driftserver.core.recording.RecordingSummary;
import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.recording.model.RecordingId;

import java.util.List;
import java.util.stream.Collectors;

public class RecordingStorage {

    private static final ModelURN RECORDINGS_PATH = ModelURN.of(new ModelId("recordings"));

    private final ModelStore modelStore;

    private RecordingId recordingId;

    public RecordingStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
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
            modelStore.write(recording, modelURN);
        } catch (ModelException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

    }

    public List<RecordingSummary> list() {
        try {
            return modelStore.listMetaData(RECORDINGS_PATH).stream()
                    .map(metaData -> new RecordingSummary(new RecordingId(metaData.getModelId().getId()), metaData.getCreatedTimeStamp()))
                    .collect(Collectors.toList());
        } catch (ModelException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
