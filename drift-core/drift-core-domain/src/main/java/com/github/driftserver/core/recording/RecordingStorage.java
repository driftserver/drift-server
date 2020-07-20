package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.metamodel.ModelException;
import com.github.driftserver.core.metamodel.ModelStore;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;

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
