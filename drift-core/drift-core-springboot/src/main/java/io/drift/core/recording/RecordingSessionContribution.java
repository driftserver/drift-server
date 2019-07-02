package io.drift.core.recording;

public interface RecordingSessionContribution {

    void onConnect(RecordingContext recordingContext);

    void initialize(RecordingContext context);

    void takeSnapshot(RecordingContext recordingContext);

    void onDisconnect(RecordingContext context);

    void onReconnect(RecordingContext context);

}
