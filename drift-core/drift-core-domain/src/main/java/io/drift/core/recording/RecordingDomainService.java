package io.drift.core.recording;

import java.util.List;

public interface RecordingDomainService {

    Recording create(RecordingDescriptor recordingDescriptor);

    void takeSnapShot(RecordingId recordingId);

    RecordingContext getById(RecordingId recordingId);

    List<RecordingSummary> getRecordings();

    void closeSession(RecordingId recordingId);

}
