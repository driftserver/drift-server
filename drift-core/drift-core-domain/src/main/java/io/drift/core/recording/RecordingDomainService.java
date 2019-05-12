package io.drift.core.recording;

public interface RecordingDomainService {

    Recording create(RecordingDescriptor recordingDescriptor);

    void start(RecordingId recordingId);

    void takeSnapShot(RecordingId recordingId);

    void finish(RecordingId recordingId);

    Recording getById(RecordingId recordingId);

    void save(RecordingId recordingId);
}
