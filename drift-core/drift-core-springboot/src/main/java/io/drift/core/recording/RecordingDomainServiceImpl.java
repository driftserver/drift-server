package io.drift.core.recording;

import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SystemDescription;
import io.drift.core.systemdescription.SystemDescriptionStorage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RecordingDomainServiceImpl implements RecordingDomainService {

    private Map<RecordingId, RecordingContext> contexts = new HashMap<>();

    private final RecordingStorage recordingStorage;
    private final SystemDescriptionStorage systemDescriptionStorage;
    private final List<RecordingSessionContribution> contributions;

    public RecordingDomainServiceImpl(RecordingStorage recordingStorage, SystemDescriptionStorage systemDescriptionStorage, List<RecordingSessionContribution> recordingSessionContribution) {
        this.recordingStorage = recordingStorage;
        this.systemDescriptionStorage = systemDescriptionStorage;
        this.contributions = recordingSessionContribution;
    }

    @Override
    public Recording create(RecordingDescriptor recordingDescriptor) {
        RecordingId recordingId = recordingDescriptor.getRecordingId();
        EnvironmentKey environmentKey =recordingDescriptor.getEnvironmentKey();

        Recording recording = new Recording(recordingId);
        recording.setEnvironmentKey(environmentKey);

        SystemDescription systemDescription = systemDescriptionStorage.load();
        RecordingContext recordingContext = new RecordingContext(recording, systemDescription);
        contexts.put(recordingId, recordingContext);
        return recording;
    }

    @Override
    public void start(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        contributions.forEach(contribution -> contribution.start(context));
    }

    private RecordingContext restoreContext(RecordingId recordingId) {
        RecordingContext context = contexts.get(recordingId);
        if (context == null) {
            Recording recording = recordingStorage.load(recordingId);
            SystemDescription systemDescription = systemDescriptionStorage.load();
            context = new RecordingContext(recording, systemDescription);
            contexts.put(recordingId, context);
        }
        return context;
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
        recordingStorage.store(getById(recordingId));
    }
}
