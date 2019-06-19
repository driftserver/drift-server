package io.drift.ui.app.flux.recording;

import io.drift.core.recording.RecordingId;

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
