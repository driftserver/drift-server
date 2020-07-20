package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.RecordingId;

import java.time.LocalDateTime;

public class RecordingSummary {

    private RecordingId recordingId;

    private LocalDateTime timeStamp;

    private String name;

    public RecordingSummary(RecordingId recordingId, LocalDateTime timeStamp, String name) {
        this.recordingId = recordingId;
        this.timeStamp = timeStamp;
        this.name = name;
    }

    public RecordingId getRecordingId() {
        return recordingId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
