package io.drift.plugin.jdbc;

import io.drift.core.recording.ActionLogger;
import io.drift.core.recording.ProblemDescription;
import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.core.system.SystemDescription;
import io.drift.core.systemdescription.SystemConnectivityTestContribution;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.drift.plugin.jdbc.DriftJDBCAutoConfig.JDBC_SUBSYSTEM_TYPE;
import static io.drift.plugin.jdbc.JDBCExceptionWrapper.wrap;

@Component
public class JDBCConnectivityTestContribution implements SystemConnectivityTestContribution {

    private final JDBCConnectionManager connectionManager;

    public JDBCConnectivityTestContribution(JDBCConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void testConnectivity(EnvironmentKey environmentKey, SystemDescription systemDescription, ActionLogger actionLogger) {

        String location = null;
        String action = null;
        try {
            location = "all jdbc subsystems";
            action = "getting connection details";

            Map<SubSystemKey, SubSystemConnectionDetails> jdbcSubSystems =
                    systemDescription.getConnectionDetails(environmentKey, JDBC_SUBSYSTEM_TYPE);

            for (SubSystemKey subSystemKey : jdbcSubSystems.keySet()) {
                try {

                    location = subSystemKey.getName();
                    JDBCConnectionDetails connectionDetails = (JDBCConnectionDetails) jdbcSubSystems.get(subSystemKey);

                    action = "testing jdbc connection";
                    JDBCRecordingSession session = new JDBCRecordingSession(connectionDetails, connectionManager, subSystemKey);
                    session.open();

                    session.close();
                } catch (Exception e) {
                    actionLogger.addProblem(new ProblemDescription(location, action, wrap(e)));
                }

            }
        } catch (Exception e) {
            actionLogger.addProblem(new ProblemDescription(location, action, wrap(e)));
        }

    }

}
