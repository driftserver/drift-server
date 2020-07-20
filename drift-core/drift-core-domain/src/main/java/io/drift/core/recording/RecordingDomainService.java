package io.drift.core.recording;

import io.drift.core.recording.model.Recording;
import io.drift.core.recording.model.RecordingId;
import io.drift.core.recording.session.RecordingContext;

import java.util.List;

public interface RecordingDomainService {

    Recording create(RecordingDescriptor recordingDescriptor);

    void takeSnapShot(RecordingId recordingId);

    RecordingContext getById(RecordingId recordingId);

    List<RecordingSummary> getRecordings();

    void closeSession(RecordingId recordingId);

}
