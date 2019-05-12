package io.drift.ui.app.flux;


import io.drift.core.recording.Recording;
import io.drift.core.recording.RecordingDescriptor;
import io.drift.core.recording.RecordingDomainService;
import io.drift.core.recording.RecordingId;
import io.drift.core.store.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordingActions {

    @Autowired
    private RecordingDomainService recordingDomainService;

    @Autowired
    private IDGenerator idGenerator;

    public Recording create() {
        RecordingDescriptor recordingDescriptor = RecordingDescriptor.builder()
                .withRecordingId(new RecordingId(idGenerator.createId()))
                .build();
        return recordingDomainService.create(recordingDescriptor);
    }


    public void start(RecordingId recordingId) {
        recordingDomainService.start(recordingId);
    }

    public void takeSnapshot(RecordingId recordingId) {
        recordingDomainService.takeSnapShot(recordingId);
    }

    public void finish(RecordingId recordingId) {
        recordingDomainService.finish(recordingId);
    }

    public void save(RecordingId recordingId) {
        recordingDomainService.save(recordingId);
    }
}
