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

    private SystemDescription systemDescription;

    private RecordingStep currentStep;

    public RecordingContext(Recording recording, SystemDescription systemDescription) {
        this.recording = recording;
        this.systemDescription = systemDescription;
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

    public Map<SubSystemKey, SubSystemConnectionDetails> getSubSystemDetails(String subsystemType) {
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
}
