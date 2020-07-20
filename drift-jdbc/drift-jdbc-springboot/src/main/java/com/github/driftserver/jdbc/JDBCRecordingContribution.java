package com.github.driftserver.jdbc;

import com.github.driftserver.core.infra.logging.ProblemDescription;
import com.github.driftserver.core.recording.session.RecordingContext;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.recording.RecordingSessionContribution;
import com.github.driftserver.core.system.SubSystemConnectionDetails;
import com.github.driftserver.core.system.SubSystemKey;
import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.metadata.DBMetaData;
import com.github.driftserver.jdbc.domain.system.JDBCConnectionDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.driftserver.jdbc.DriftJDBCAutoConfig.JDBC_SUBSYSTEM_TYPE;

@Component
public class JDBCRecordingContribution implements RecordingSessionContribution {

    private final JDBCConnectionManager connectionManager;

    private Map<RecordingId, List<JDBCRecordingSession>> sessionsById = new HashMap<>();

    public JDBCRecordingContribution(JDBCConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void onConnect(RecordingContext recordingContext) {
        Map<SubSystemKey, SubSystemConnectionDetails> jdbcSubSystems = null;
        try {
            jdbcSubSystems = recordingContext.getSubSystems(JDBC_SUBSYSTEM_TYPE);
        } catch (Exception e) {
            recordingContext.getActionLogger().addProblem(new ProblemDescription("jdbc subsystems", "getting jdbc connection details", new DriftJDBCContributionException(DriftJDBCContributionExceptionType.NO_CONNECTION_DETAILS_ERROR, e)));
            return;
        }

        List<JDBCRecordingSession> jdbcRecordingSessions = new ArrayList<>();
        sessionsById.put(recordingContext.getRecordingId(), jdbcRecordingSessions);

        jdbcSubSystems.forEach((subSystemKey, jdbcSubSystem) -> {
            String location = subSystemKey.getName();
            String action = null;
            try {
                action = "getting connection details";
                JDBCConnectionDetails connectionDetails = (JDBCConnectionDetails) jdbcSubSystem;

                action = "creating new jdbc recording session";
                JDBCRecordingSession session = new JDBCRecordingSession(connectionDetails, connectionManager, subSystemKey);

                session.open();

                jdbcRecordingSessions.add(session);
            } catch (Exception e) {
                recordingContext.getActionLogger().addProblem(new ProblemDescription(location, action, JDBCExceptionWrapper.wrap(e)));
            }
        });
    }

    public void initialize(RecordingContext recordingContext) {

        List<JDBCRecordingSession> sessions = sessionsById.get(recordingContext.getRecordingId());

        sessions.forEach(session -> {

            String subSystemName = session.getSubSystemKey().getName();
            String action = null;
            try {

                session.initialize();

                action = "adding db metadata as subsystem description to recording";
                recordingContext.getRecording().addSubSystemDescription(subSystemName, session.getDbMetaData());

                action = "taking initial snapshot";
                session.takeSnapshot();

                action = "saving snapshot as initial state of the database ";
                recordingContext.getRecording().getInitialState().addSubSystemState(subSystemName, session.getLastSnapshot());

                action = "saving snapshot as current final state of the database";
                recordingContext.getRecording().getFinalstate().addSubSystemState(subSystemName, session.getLastSnapshot());
            } catch (Exception e) {
                recordingContext.getActionLogger().addProblem(new ProblemDescription(subSystemName, action, JDBCExceptionWrapper.wrap(e)));
            }

        });

    }

    @Override
    public void onReconnect(RecordingContext recordingContext) {
        List<JDBCRecordingSession> sessions = sessionsById.get(recordingContext.getRecordingId());

        sessions.forEach(session -> {

            String subSystemName = session.getSubSystemKey().getName();
            String action = null;
            try {
                action = "getting last snapshot from final state";
                DBSnapShot lastDBSnapshot = (DBSnapShot) recordingContext.getRecording().getFinalstate().getSubSystemState(subSystemName);

                action = "getting db metadata from subsystem description";
                DBMetaData dbMetaData = (DBMetaData) recordingContext.getRecording().getSubSystemDescription(subSystemName);

                action = "reconnecting jdbc session";
                session.reconnect(lastDBSnapshot, dbMetaData);
            } catch (Exception e) {
                recordingContext.getActionLogger().addProblem(new ProblemDescription(subSystemName, action, JDBCExceptionWrapper.wrap(e)));
            }

        });
    }

    public void takeSnapshot(RecordingContext recordingContext) {
        List<JDBCRecordingSession> sessions = sessionsById.get(recordingContext.getRecordingId());

        sessions.forEach(session -> {

            String location = session.getSubSystemKey().getName();
            String action = null;

            try {
                action = "taking snapshot";
                session.takeSnapshot();

                action = "adding delta to system interactions";
                recordingContext.getCurrentStep().getSystemInteractions().add(session.getLastDelta());

                action = "setting initial system state";
                recordingContext.getRecording().getInitialState().addSubSystemState(session.getSubSystemKey().getName(), session.getLastSnapshot());

                action = "setting final system state";
                recordingContext.getRecording().getFinalstate().addSubSystemState(session.getSubSystemKey().getName(), session.getLastSnapshot());
            } catch (Exception e) {
                e.printStackTrace();
                recordingContext.getActionLogger().addProblem(new ProblemDescription(location, action, JDBCExceptionWrapper.wrap(e)));
            }
        });

    }

    @Override
    public void onDisconnect(RecordingContext recordingContext) {
        RecordingId recordingId = recordingContext.getRecordingId();
        sessionsById.get(recordingId).forEach(JDBCRecordingSession::close);
        sessionsById.remove(recordingId);
    }

}
