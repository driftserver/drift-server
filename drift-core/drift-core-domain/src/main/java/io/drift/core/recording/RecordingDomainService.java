package io.drift.core.recording;

import java.util.List;

public interface RecordingDomainService {

    Recording create(RecordingDescriptor recordingDescriptor);

    void connect(RecordingId recordingId);

    void takeSnapShot(RecordingId recordingId);

    void disconnect(RecordingId recordingId);

    Recording getById(RecordingId recordingId);

    void save(RecordingId recordingId);

    List<RecordingSummary> getRecordings();

    RecordingSessionSettings getRecordingSessionSettings(RecordingId recordingId);

    boolean isConnected(RecordingId recordingId);

    void closeSession(RecordingId recordingId);

    ActionResult getActionResult(RecordingId recordingId);

}
