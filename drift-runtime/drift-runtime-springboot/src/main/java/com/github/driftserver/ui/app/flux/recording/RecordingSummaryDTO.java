package com.github.driftserver.ui.app.flux.recording;

import com.github.driftserver.core.recording.model.RecordingId;

import java.io.Serializable;

public class RecordingSummaryDTO implements Serializable {

    private RecordingId recordingId;

    public RecordingSummaryDTO(RecordingId recordingId) {
        this.recordingId = recordingId;
    }

    public RecordingId getRecordingId() {
        return recordingId;
    }

}
