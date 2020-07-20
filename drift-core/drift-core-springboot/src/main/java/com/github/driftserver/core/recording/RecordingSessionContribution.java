package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.session.RecordingContext;

public interface RecordingSessionContribution {

    void onConnect(RecordingContext recordingContext);

    void initialize(RecordingContext context);

    void takeSnapshot(RecordingContext recordingContext);

    void onDisconnect(RecordingContext context);

    void onReconnect(RecordingContext context);

}
