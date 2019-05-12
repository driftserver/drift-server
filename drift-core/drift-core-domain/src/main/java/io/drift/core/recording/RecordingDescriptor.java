package io.drift.core.recording;

public class RecordingDescriptor {

    private RecordingId recordingId;

    public RecordingDescriptor(RecordingId recordingId) {
        this.recordingId = recordingId;
    }

    public RecordingId getRecordingId() {
        return recordingId;
    }

    static public class Builder {

        private RecordingId recordingId;

        public Builder withRecordingId(RecordingId recordingId) {
            this.recordingId = recordingId;
            return this;
        }

        public RecordingDescriptor build() {
            return new RecordingDescriptor(recordingId);
        }
    }

    static public Builder builder() { return new Builder(); }

}
