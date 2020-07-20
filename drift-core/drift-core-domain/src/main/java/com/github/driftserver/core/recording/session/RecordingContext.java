package com.github.driftserver.core.recording.session;

import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.infra.logging.ActionLogger;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.recording.model.RecordingStep;
import com.github.driftserver.core.system.EnvironmentKey;
import com.github.driftserver.core.system.SubSystemConnectionDetails;
import com.github.driftserver.core.system.SubSystemKey;
import com.github.driftserver.core.system.SystemDescription;

import java.util.Map;

public class RecordingContext {

    private Recording recording;

    private RecordingState state;

    private SystemDescription systemDescription;

    private ActionLogger actionLogger;

    private RecordingStep currentStep;

    private RecordingSessionSettings settings = new RecordingSessionSettings();

    public RecordingContext(Recording recording, SystemDescription systemDescription) {
        this.recording = recording;
        this.systemDescription = systemDescription;
        actionLogger = new ActionLogger(false);
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

    public Map<SubSystemKey, SubSystemConnectionDetails> getSubSystems(String subsystemType) {
        EnvironmentKey environmentKey = recording.getEnvironmentKey();
        return systemDescription.getConnectionDetails(environmentKey, subsystemType);
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

    public ActionLogger getActionLogger() {
        return actionLogger;
    }

    public void startAsyncAction() {
        actionLogger = new ActionLogger(true);
    }

    public void startSynchronousAction() {
        actionLogger = new ActionLogger(false);
    }

    public boolean isConnected() {
        return RecordingState.CONNECTED.equals(state);
    }

    public boolean isInitialized() {
        return recording.getInitialState() != null;
    }
}
