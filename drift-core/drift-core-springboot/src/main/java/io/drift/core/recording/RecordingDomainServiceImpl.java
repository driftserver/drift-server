package io.drift.core.recording;

import io.drift.core.store.ModelStore;
import io.drift.core.store.ModelStoreException;
import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RecordingDomainServiceImpl implements RecordingDomainService {

    private Map<RecordingId, RecordingContext> recordingContextsByKey = new HashMap<>();

    public static final StorageId STORAGE_ID_RECORDINGS = new StorageId("recordings");

    private final ModelStore modelStore;
    private final List<RecordingSessionContribution> contributions;

    public RecordingDomainServiceImpl(ModelStore modelStore, List<RecordingSessionContribution> recordingSessionContribution) {
        this.modelStore = modelStore;
        this.contributions = recordingSessionContribution;
    }

    private StoragePath resolveRecordingPath(RecordingId recordingId) {
        return resolveRecordingsPath().resolve(recordingId);
    }

    private StoragePath resolveRecordingsPath() {
        return StoragePath.of(STORAGE_ID_RECORDINGS);
    }


    @Override
    public Recording create(RecordingDescriptor recordingDescriptor) {
        RecordingId recordingId = recordingDescriptor.getRecordingId();
        Recording recording = new Recording(recordingId);
        RecordingContext recordingContext = new RecordingContext(recording);
        recordingContextsByKey.put(recordingId, recordingContext);
        return recording;
    }

    @Override
    public void start(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        contributions.forEach(contribution -> contribution.start(context));
    }

    private RecordingContext restoreContext(RecordingId recordingId) {
        try {
            RecordingContext recordingContext = recordingContextsByKey.get(recordingId);
            if (recordingContext == null) {
                Recording recording = modelStore.load(resolveRecordingPath(recordingId), Recording.class);
                recordingContext = new RecordingContext(recording);
                recordingContextsByKey.put(recordingId, recordingContext);
            }
            return recordingContext;
        } catch (ModelStoreException e) {
            e.printStackTrace(); ;
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void takeSnapShot(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        RecordingStep step = new RecordingStep();
        step.setTitle("step " + context.getRecording().getSteps().size());
        context.getRecording().addStep(step);
        context.setCurrentStep(step);
        contributions.forEach(contribution -> contribution.takeSnapshot(context));
    }

    @Override
    public void finish(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        contributions.forEach(contribution -> contribution.finish(context));
    }

    @Override
    public Recording getById(RecordingId recordingId) {
        return restoreContext(recordingId).getRecording();
    }

    @Override
    public void save(RecordingId recordingId) {
        try {
            modelStore.save(getById(recordingId), resolveRecordingPath(recordingId));
        } catch (ModelStoreException e) {
            e.printStackTrace();
        }
    }
}
