package com.github.driftserver.ui.app.flux.recording;

import com.github.driftserver.core.recording.model.RecordingId;

import java.io.Serializable;

public class RecordingSummaryDTO implements Serializable {

    private RecordingId recordingId;

    private String label;

    public RecordingSummaryDTO(RecordingId recordingId, String label) {
        this.recordingId = recordingId;
        this.label = label;
    }

    public RecordingId getRecordingId() {
        return recordingId;
    }

    public String getLabel() {
        return label;
    }
}
