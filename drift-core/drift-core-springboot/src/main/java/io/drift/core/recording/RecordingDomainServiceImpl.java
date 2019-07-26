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
        EnvironmentKey environmentKey = recordingDescriptor.getEnvironmentKey();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Recording recording = new Recording(recordingId);
        recording.setName("Recording " + environmentKey.getName() + " " + LocalDateTime.now().format(formatter));
        recording.setEnvironmentKey(environmentKey);

        SystemDescription systemDescription = systemDescriptionStorage.load();
        RecordingContext recordingContext = new RecordingContext(recording, systemDescription);
        recordingContext.setState(RecordingState.CREATED);

        contexts.put(recordingId, recordingContext);

        return recording;
    }

    @Override
    public RecordingContext getById(RecordingId recordingId) {
        return restoreContext(recordingId);
    }

    @Override
    public void takeSnapShot(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);

        if (!context.isConnected()) {
            connect(context);
            if (context.getActionLogger().hasProblems()) return;

            if (!context.isInitialized()) {
                initialize(context);
            } else {
                reinitialize(context);
            }

        }

        context.startSynchronousAction();
        RecordingStep step = new RecordingStep();
        step.setTitle("step " + context.getRecording().getSteps().size());
        context.getRecording().addStep(step);
        context.setCurrentStep(step);

        context.getRecording().setFinalstate(new SystemState());
        contributions.forEach(contribution -> contribution.takeSnapshot(context));

        autoSave(context);

    }


    @Override
    public List<RecordingSummary> getRecordings() {
        return recordingStorage.list();
    }

    @Override
    public void closeSession(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        if (context.getState() != RecordingState.DISCONNECTED) {
            disconnect(context);
        }
        contexts.remove(recordingId);
    }

    private void connect(RecordingContext context) {
        context.startAsyncAction();
        contributions.forEach(contribution -> contribution.onConnect(context));

        if (!context.getActionLogger().hasProblems()) {
            context.setState(RecordingState.CONNECTED);
            autoSave(context);
        }
    }

    private void initialize(RecordingContext context) {
        context.startSynchronousAction();

        Recording recording = context.getRecording();
        recording.setInitialState(new SystemState());
        recording.setFinalstate(new SystemState());

        contributions.forEach(contribution -> contribution.initialize(context));

    }

    private void reinitialize(RecordingContext context) {
        context.startSynchronousAction();
        contributions.forEach(contribution -> contribution.onReconnect(context));
    }

    private void disconnect(RecordingContext context) {
        context.setState(RecordingState.DISCONNECTED);
        contributions.forEach(contribution -> contribution.onDisconnect(context));
        autoSave(context);
    }

    private void save(RecordingContext recordingContext) {
        recordingStorage.store(recordingContext.getRecording());
    }

    private void autoSave(RecordingContext context) {
        if (context.getSettings().isAutoSave()) save(context);
    }

    private RecordingContext restoreContext(RecordingId recordingId) {
        RecordingContext context = contexts.get(recordingId);
        if (context == null) {
            Recording recording = recordingStorage.load(recordingId);
            SystemDescription systemDescription = systemDescriptionStorage.load();
            context = new RecordingContext(recording, systemDescription);
            context.setState(RecordingState.DISCONNECTED);
            contexts.put(recordingId, context);
        }
        return context;
    }

}
