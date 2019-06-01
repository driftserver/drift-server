package io.drift.core.recording;

public interface RecordingSessionContribution {

    void onFirstConnect(RecordingContext context);

    void takeSnapshot(RecordingContext context);

    void onDisconnect(RecordingContext context);

    void onReconnect(RecordingContext context);

}
