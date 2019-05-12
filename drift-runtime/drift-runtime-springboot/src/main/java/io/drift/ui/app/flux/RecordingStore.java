package io.drift.ui.app.flux;

import io.drift.core.recording.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordingStore {

    @Autowired
    RecordingDomainService service;

    public Recording getRecording(RecordingId recordingId) {
        return service.getById(recordingId);
    }

    public RecordingStep getRecordingStep(RecordingId recordingId, int idxStep) {
        Recording recording = getRecording(recordingId);
        return recording.getSteps().size() == 0 ? null : recording.getSteps().get(idxStep);
    }

    public int getRecordingStepCount(RecordingId recordingId) {
        return getRecording(recordingId).getSteps().size();
    }

    public SubSystemDescription getSubSystemDescription(RecordingId recordingId, String subSystem) {
        return getRecording(recordingId).getSubSystemDescription(subSystem);
    }

}
