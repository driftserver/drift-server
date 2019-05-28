package io.drift.core.recording;

public class RecordingSessionSettings {

    private boolean autoSave = true;

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }
}
