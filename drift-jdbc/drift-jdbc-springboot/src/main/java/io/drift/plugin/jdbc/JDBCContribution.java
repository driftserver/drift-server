package io.drift.plugin.jdbc;

import io.drift.core.recording.RecordingContext;
import io.drift.core.recording.RecordingId;
import io.drift.core.recording.RecordingSessionContribution;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JDBCContribution implements RecordingSessionContribution {

    public static final String SUBSYSTEM_TYPE = "jdbc";

    private final JDBCConnectionManager connectionManager;

    private Map<RecordingId, List<JDBCRecordingSession>> sessionsById = new HashMap<>();

    public JDBCContribution(JDBCConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void start(RecordingContext recordingContext) {

        Map<SubSystemKey, SubSystemConnectionDetails> jdbcSubSystems = recordingContext.getSubSystems(SUBSYSTEM_TYPE);

        List<JDBCRecordingSession> jdbcRecordingSessions = new ArrayList<>();
        sessionsById.put(recordingContext.getRecordingId(), jdbcRecordingSessions);

        jdbcSubSystems.forEach((subSystemKey, jdbcSubSystem) -> {

            JDBCRecordingSession session = new JDBCRecordingSession((JDBCConnectionDetails) jdbcSubSystem, connectionManager, subSystemKey);
            jdbcRecordingSessions.add(session);

            recordingContext.getRecording().addSubSystemDescription(subSystemKey.getName(), session.getDbMetaData());

            session.takeSnapshot();

            recordingContext.getRecording().getInitialState().addSubSystemState(subSystemKey.getName(), session.getLastSnapshot());
            recordingContext.getRecording().getFinalstate().addSubSystemState(subSystemKey.getName(), session.getLastSnapshot());

        });


    }

    public void takeSnapshot(RecordingContext recordingContext) {
        List<JDBCRecordingSession> sessions = sessionsById.get(recordingContext.getRecordingId());

        sessions.forEach(session -> {
            session.takeSnapshot();
            recordingContext.getCurrentStep().getSystemInteractions().add(session.getLastDelta());
            recordingContext.getRecording().getInitialState().addSubSystemState(session.getSubSystemKey().getName(), session.getLastSnapshot());
            recordingContext.getRecording().getFinalstate().addSubSystemState(session.getSubSystemKey().getName(), session.getLastSnapshot());
        });

    }

    @Override
    public void finish(RecordingContext recordingContext) {
        RecordingId recordingId = recordingContext.getRecordingId();
        sessionsById.get(recordingId).forEach(JDBCRecordingSession::close);
        sessionsById.remove(recordingId);
    }

}
