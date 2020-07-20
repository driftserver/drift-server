package io.drift.ui.app.flux.recording;

import io.drift.core.infra.logging.ActionLogger;
import io.drift.core.recording.*;
import io.drift.core.recording.model.Recording;
import io.drift.core.recording.model.RecordingId;
import io.drift.core.recording.model.RecordingStep;
import io.drift.core.recording.model.SubSystemDescription;
import io.drift.core.recording.session.RecordingContext;
import io.drift.core.recording.session.RecordingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecordingStore {

    @Autowired
    RecordingDomainService service;

    public Recording getRecording(RecordingId recordingId) {
        return getContext(recordingId).getRecording();
    }

    public RecordingStep getRecordingStep(RecordingId recordingId, int idxStep) {
        Recording recording = getRecording(recordingId);
        return recording.getSteps().size() == 0 ? null : recording.getSteps().get(idxStep);
    }

    public SubSystemDescription getSubSystemDescription(RecordingId recordingId, String subSystem) {
        return getRecording(recordingId).getSubSystemDescription(subSystem);
    }

    public RecorderControlDTO getRecorderControlState(RecordingId recordingId) {
        RecordingContext context = getContext(recordingId);
        boolean isAutoSave = getContext(recordingId).getSettings().isAutoSave();
        boolean isConnected = context.getState().equals(RecordingState.CONNECTED);
        return new RecorderControlDTO(isAutoSave, isConnected);
    }

    private RecordingContext getContext(RecordingId recordingId) {
        return service.getById(recordingId);
    }

    public List<RecordingSummaryDTO> getRecordingSummaries() {
        return service.getRecordings().stream()
                .map(summary -> new RecordingSummaryDTO(summary.getRecordingId(), summary.getName()))
                .collect(Collectors.toList());
    }

    public ActionLogger getActionResult(RecordingId recordingId) {
        return getContext(recordingId).getActionLogger();
    }
}
