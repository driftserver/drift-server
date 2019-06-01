package io.drift.plugin.jdbc;

import io.drift.core.recording.RecordingContext;
import io.drift.core.recording.RecordingId;
import io.drift.core.recording.RecordingSessionContribution;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JDBCContribution implements RecordingSessionContribution {

    public static final String JDBC_SUBSYSTEM_TYPE = "jdbc";

    private final JDBCConnectionManager connectionManager;

    private Map<RecordingId, List<JDBCRecordingSession>> sessionsById = new HashMap<>();

    public JDBCContribution(JDBCConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void onFirstConnect(RecordingContext recordingContext) {

        Map<SubSystemKey, SubSystemConnectionDetails> jdbcSubSystems = recordingContext.getSubSystems(JDBC_SUBSYSTEM_TYPE);

        List<JDBCRecordingSession> jdbcRecordingSessions = new ArrayList<>();
        sessionsById.put(recordingContext.getRecordingId(), jdbcRecordingSessions);

        jdbcSubSystems.forEach((subSystemKey, jdbcSubSystem) -> {

            JDBCConnectionDetails connectionDetails = (JDBCConnectionDetails) jdbcSubSystem;
            JDBCRecordingSession session = new JDBCRecordingSession(connectionDetails, connectionManager, subSystemKey);
            jdbcRecordingSessions.add(session);

            recordingContext.getRecording().addSubSystemDescription(subSystemKey.getName(), session.getDbMetaData());

            session.takeSnapshot();

            recordingContext.getRecording().getInitialState().addSubSystemState(subSystemKey.getName(), session.getLastSnapshot());
            recordingContext.getRecording().getFinalstate().addSubSystemState(subSystemKey.getName(), session.getLastSnapshot());

        });

    }

    @Override
    public void onReconnect(RecordingContext recordingContext) {
        Map<SubSystemKey, SubSystemConnectionDetails> jdbcSubSystems = recordingContext.getSubSystems(JDBC_SUBSYSTEM_TYPE);

        List<JDBCRecordingSession> jdbcRecordingSessions = new ArrayList<>();
        sessionsById.put(recordingContext.getRecordingId(), jdbcRecordingSessions);

        jdbcSubSystems.forEach((subSystemKey, jdbcSubSystem) -> {

            JDBCConnectionDetails connectionDetails = (JDBCConnectionDetails) jdbcSubSystem;
            DBSnapShot lastDBSnapshot = (DBSnapShot) recordingContext.getRecording().getFinalstate().getSubSystemState(subSystemKey.getName());
            DBMetaData dbMetaData = (DBMetaData) recordingContext.getRecording().getSubSystemDescription(subSystemKey.getName());

            JDBCRecordingSession session = new JDBCRecordingSession(connectionDetails, connectionManager, subSystemKey, dbMetaData, lastDBSnapshot);
            jdbcRecordingSessions.add(session);

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
    public void onDisconnect(RecordingContext recordingContext) {
        RecordingId recordingId = recordingContext.getRecordingId();
        sessionsById.get(recordingId).forEach(JDBCRecordingSession::close);
        sessionsById.remove(recordingId);
    }

}
