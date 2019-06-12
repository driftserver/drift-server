package io.drift.core.recording;

import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.core.system.SystemDescription;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecordingContext {

    private Recording recording;

    private RecordingState state;

    private SystemDescription systemDescription;

    private ActionResult actionResult;

    private RecordingStep currentStep;

    private RecordingSessionSettings settings = new RecordingSessionSettings();

    public RecordingContext(Recording recording, SystemDescription systemDescription, ActionResult actionResult) {
        this.recording = recording;
        this.systemDescription = systemDescription;
        this.actionResult = actionResult;
    }

    public Recording getRecording() {
        return recording;
    }

    public RecordingStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(RecordingStep currentStep) {
        this.currentStep = currentStep;
    }

    public SystemDescription getSystemDescription() {
        return systemDescription;
    }

    public Map<SubSystemKey, SubSystemConnectionDetails> getSubSystems(String subsystemType) {
        EnvironmentKey environmentKey = recording.getEnvironmentKey();
        return systemDescription.getConnectionDetails(environmentKey, subsystemType);
    }

    public List<SubSystemKey> getSubSystemKeys(String subsystemType) {
        return systemDescription.getSubSystems().stream()
                .filter(subSystem -> subSystem.getType().equals(subsystemType))
                .map(subSystem -> subSystem.getKey())
                .collect(Collectors.toList());
    }

    public RecordingId getRecordingId() {
        return recording.getId();
    }

    public RecordingState getState() {
        return state;
    }

    public void setState(RecordingState state) {
        this.state = state;
    }

    public RecordingSessionSettings getSettings() {
        return settings;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }
}
