package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.system.EnvironmentKey;

public class RecordingDescriptor {

    private RecordingId recordingId;

    private EnvironmentKey environmentKey;

    public RecordingDescriptor(RecordingId recordingId, EnvironmentKey environmentKey) {
        this.recordingId = recordingId;
        this.environmentKey = environmentKey;
    }

    public RecordingId getRecordingId() {
        return recordingId;
    }

    public EnvironmentKey getEnvironmentKey() {
        return environmentKey;
    }

    static public class Builder {

        private RecordingId recordingId;

        private EnvironmentKey environmentKey;

        public Builder withRecordingId(RecordingId recordingId) {
            this.recordingId = recordingId;
            return this;
        }

        public Builder withEnvironmentKey(EnvironmentKey environmentKey) {
            this.environmentKey = environmentKey;
            return this;
        }

        public RecordingDescriptor build() {
            return new RecordingDescriptor(recordingId, environmentKey);
        }
    }

    static public Builder builder() { return new Builder(); }

}
