package io.drift.core.recording;

import java.util.List;

public interface RecordingDomainService {

    Recording create(RecordingDescriptor recordingDescriptor);

    void start(RecordingId recordingId);

    void takeSnapShot(RecordingId recordingId);

    void finish(RecordingId recordingId);

    Recording getById(RecordingId recordingId);

    void save(RecordingId recordingId);

    List<RecordingSummary> getRecordings();

    RecordingSessionSettings getRecordingSessionSettings(RecordingId recordingId);

}
