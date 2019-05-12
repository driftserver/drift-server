package io.drift.core.recording;

public class RecordingContext {

    private Recording recording;

    private RecordingStep currentStep;

    public RecordingContext(Recording recording) {
        this.recording = recording;
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

}
