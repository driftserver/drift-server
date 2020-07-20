package io.drift.core.recording;

import io.drift.core.recording.session.RecordingContext;

public interface RecordingSessionContribution {

    void onConnect(RecordingContext recordingContext);

    void initialize(RecordingContext context);

    void takeSnapshot(RecordingContext recordingContext);

    void onDisconnect(RecordingContext context);

    void onReconnect(RecordingContext context);

}
