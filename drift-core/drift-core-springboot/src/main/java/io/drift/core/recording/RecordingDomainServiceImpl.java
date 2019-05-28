package io.drift.core.recording;

import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SystemDescription;
import io.drift.core.systemdescription.SystemDescriptionStorage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Recording recording = new Recording(recordingId);
        recording.setName("Recording " + environmentKey.getName() + " " + LocalDateTime.now().format(formatter));
        recording.setEnvironmentKey(environmentKey);
        recording.setInitialState(new SystemState());
        recording.setFinalstate(new SystemState());

        SystemDescription systemDescription = systemDescriptionStorage.load();
        RecordingContext recordingContext = new RecordingContext(recording, systemDescription);
        recordingContext.setState(RecordingState.CREATED);

        contexts.put(recordingId, recordingContext);
        return recording;
    }

    @Override
    public void start(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        context.setState(RecordingState.CONNECTED);
        contributions.forEach(contribution -> contribution.start(context));

        if (context.getSettings().isAutoSave()) save(recordingId);
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

        context.getRecording().setFinalstate(new SystemState());
        contributions.forEach(contribution -> contribution.takeSnapshot(context));

        if (context.getSettings().isAutoSave()) save(recordingId);

    }

    @Override
    public void finish(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        context.setState(RecordingState.DISCONNECTED);
        contributions.forEach(contribution -> contribution.finish(context));

        if (context.getSettings().isAutoSave()) save(recordingId);

    }

    @Override
    public Recording getById(RecordingId recordingId) {
        return restoreContext(recordingId).getRecording();
    }

    @Override
    public void save(RecordingId recordingId) {
        recordingStorage.store(getById(recordingId));
    }

    @Override
    public List<RecordingSummary> getRecordings() {
        return recordingStorage.list();
    }

    @Override
    public RecordingSessionSettings getRecordingSessionSettings(RecordingId recordingId) {
        return restoreContext(recordingId).getSettings();
    }
}
