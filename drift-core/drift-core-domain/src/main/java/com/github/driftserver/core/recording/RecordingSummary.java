package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.RecordingId;

import java.time.LocalDateTime;

public class RecordingSummary {

    private RecordingId recordingId;

    private LocalDateTime timeStamp;

    public RecordingSummary(RecordingId recordingId, LocalDateTime timeStamp) {
        this.recordingId = recordingId;
        this.timeStamp = timeStamp;
    }

    public RecordingId getRecordingId() {
        return recordingId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}
