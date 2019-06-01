package io.drift.ui.app.flux;


import io.drift.core.recording.Recording;
import io.drift.core.recording.RecordingDescriptor;
import io.drift.core.recording.RecordingDomainService;
import io.drift.core.recording.RecordingId;
import io.drift.core.store.IDGenerator;
import io.drift.core.system.EnvironmentKey;
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

    public void start(RecordingId recordingId) {
        recordingDomainService.connect(recordingId);
    }

    public void takeSnapshot(RecordingId recordingId) {
        recordingDomainService.takeSnapShot(recordingId);
    }

    public void finish(RecordingId recordingId) {
        recordingDomainService.disconnect(recordingId);
    }

    public void save(RecordingId recordingId) {
        recordingDomainService.save(recordingId);
    }

    public void closeSession(RecordingId recordingId) { recordingDomainService.closeSession(recordingId); };
}
