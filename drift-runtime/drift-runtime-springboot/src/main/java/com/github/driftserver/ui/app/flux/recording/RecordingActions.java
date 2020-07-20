package com.github.driftserver.ui.app.flux.recording;


import com.github.driftserver.core.recording.model.Recording;
import com.github.driftserver.core.recording.RecordingDescriptor;
import com.github.driftserver.core.recording.RecordingDomainService;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.metamodel.id.IDGenerator;
import com.github.driftserver.core.system.EnvironmentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordingActions {

    @Autowired
    private RecordingDomainService recordingDomainService;

    @Autowired
    private IDGenerator idGenerator;

    public Recording create(EnvironmentKey environmentKey) {
        RecordingDescriptor recordingDescriptor = RecordingDescriptor.builder()
                .withRecordingId(new RecordingId(idGenerator.createId()))
                .withEnvironmentKey(environmentKey)
                .build();
        return recordingDomainService.create(recordingDescriptor);
    }

    public void takeSnapshot(RecordingId recordingId) {
        recordingDomainService.takeSnapShot(recordingId);
    }

    public void closeSession(RecordingId recordingId) { recordingDomainService.closeSession(recordingId); };
}
