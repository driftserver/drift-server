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

        ActionResult actionLogger = new ActionResult();

        SystemDescription systemDescription = systemDescriptionStorage.load();
        RecordingContext recordingContext = new RecordingContext(recording, systemDescription, actionLogger);
        recordingContext.setState(RecordingState.CREATED);

        contexts.put(recordingId, recordingContext);
        reportSessionCount();
        return recording;
    }

    @Override
    public void connect(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        connect(context);
    }

    private void connect(RecordingContext context) {
        Recording recording = context.getRecording();
        if (recording.getInitialState()==null) {
            recording.setInitialState(new SystemState());
            recording.setFinalstate(new SystemState());
            contributions.forEach(contribution -> contribution.onFirstConnect(context));
        } else {
            contributions.forEach(contribution -> contribution.onReconnect(context));
        }
        context.setState(RecordingState.CONNECTED);

        autoSave(context);
    }

    private void autoSave(RecordingContext context) {
        if (context.getSettings().isAutoSave()) save(context);
    }


    @Override
    public void takeSnapShot(RecordingId recordingId) {
        RecordingContext recordingContext = restoreContext(recordingId);

        if (!RecordingState.CONNECTED.equals(recordingContext.getState())){
            connect(recordingContext);
        } else {
            RecordingStep step = new RecordingStep();
            step.setTitle("step " + recordingContext.getRecording().getSteps().size());
            recordingContext.getRecording().addStep(step);
            recordingContext.setCurrentStep(step);

            recordingContext.getRecording().setFinalstate(new SystemState());
            contributions.forEach(contribution -> contribution.takeSnapshot(recordingContext));
        }
        autoSave(recordingContext);

    }

    @Override
    public void disconnect(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        context.setState(RecordingState.DISCONNECTED);
        contributions.forEach(contribution -> contribution.onDisconnect(context));

        autoSave(context);

    }

    @Override
    public void save(RecordingId recordingId) {
        save(restoreContext(recordingId));
    }

    private void save(RecordingContext recordingContext) {
        recordingStorage.store(recordingContext.getRecording());
    }

    @Override
    public Recording getById(RecordingId recordingId) {
        return restoreContext(recordingId).getRecording();
    }

    private RecordingContext restoreContext(RecordingId recordingId) {
        RecordingContext context = contexts.get(recordingId);
        if (context == null) {
            Recording recording = recordingStorage.load(recordingId);
            SystemDescription systemDescription = systemDescriptionStorage.load();
            ActionResult actionLogger = new ActionResult();
            context = new RecordingContext(recording, systemDescription, actionLogger);
            context.setState(RecordingState.DISCONNECTED);
            contexts.put(recordingId, context);
            reportSessionCount();
        }
        return context;
    }

    @Override
    public List<RecordingSummary> getRecordings() {
        return recordingStorage.list();
    }

    @Override
    public RecordingSessionSettings getRecordingSessionSettings(RecordingId recordingId) {
        return restoreContext(recordingId).getSettings();
    }

    @Override
    public void closeSession(RecordingId recordingId) {
        RecordingContext context = restoreContext(recordingId);
        if (context.getState() != RecordingState.DISCONNECTED) {
            disconnect(recordingId);
        }
        contexts.remove(recordingId);
        reportSessionCount();
    }

    @Override
    public boolean isConnected(RecordingId recordingId) {
        return restoreContext(recordingId).getState().equals(RecordingState.CONNECTED);
    }

    private void reportSessionCount() {
        System.out.println("#contexts: " + contexts.size());
    }

    @Override
    public ActionResult getActionResult(RecordingId recordingId) {
        return restoreContext(recordingId).getActionResult();
    }
}
