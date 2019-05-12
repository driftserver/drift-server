package io.drift.core.recording;

public interface RecordingSessionContribution {

    public void start(RecordingContext context);

    public void takeSnapshot(RecordingContext context);

    public void finish(RecordingContext context);

}
