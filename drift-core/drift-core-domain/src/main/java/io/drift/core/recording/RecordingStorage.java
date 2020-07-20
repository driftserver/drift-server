package io.drift.core.recording;

import io.drift.core.metamodel.ModelException;
import io.drift.core.metamodel.ModelStore;
import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;
import io.drift.core.recording.model.Recording;
import io.drift.core.recording.model.RecordingId;

public class RecordingStorage {

    private static final ModelURN RECORDINGS_PATH = ModelURN.of(new ModelId("recordings"));

    private final ModelStore modelStore;

    public RecordingStorage(ModelStore modelStore) {
        this.modelStore = modelStore;
    }


    public Recording load(RecordingId recordingId) {
        try {
            return modelStore.read(toUrn(recordingId), Recording.class);
        } catch (ModelException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }
    }

    private ModelURN toUrn(RecordingId recordingId) {
        return RECORDINGS_PATH.resolve(new ModelId(recordingId.getId()));
    }

    public void store(Recording recording) {
        try {
            modelStore.write(recording, toUrn(recording.getId()));
        } catch (ModelException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }

    }

}
