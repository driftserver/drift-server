package com.github.driftserver.core.recording;

import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.recording.session.RecordingContext;

import java.util.List;

public interface RecordingDomainService {

    Recording create(RecordingDescriptor recordingDescriptor);

    void takeSnapShot(RecordingId recordingId);

    RecordingContext getById(RecordingId recordingId);

    List<RecordingSummary> getRecordings();

    void closeSession(RecordingId recordingId);

}
