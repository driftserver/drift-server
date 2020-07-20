package io.drift.plugin.jdbc;

import io.drift.core.infra.logging.ProblemDescription;
import io.drift.core.system.*;
import io.drift.core.system.connectivity.SubSystemConnectivityActionContext;
import io.drift.core.systemdescription.SystemConnectivityTestContribution;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static io.drift.plugin.jdbc.JDBCExceptionWrapper.wrap;

@Component
public class JDBCConnectivityTestContribution implements SystemConnectivityTestContribution {

    private final JDBCConnectionManager connectionManager;

    public JDBCConnectivityTestContribution(JDBCConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public String getSubSystemType() {
        return "jdbc";
    }

    @Async
    @Override
    public void asyncTestConnectivity(SubSystemConnectivityActionContext actionContext) {

        SubSystemKey subSystemKey = actionContext.getSubSystem().getKey();
        String location = subSystemKey.getName();
        String action = null;

        try {

            action = "getting jdbc connection details";
            JDBCConnectionDetails jdbcConnectionDetails = (JDBCConnectionDetails) actionContext.getConnectionDetails();

            action = "closing existing jdbc connections";
            connectionManager.stopDataSource(jdbcConnectionDetails);

            action = "testing jdbc connection";
            connectionManager.getDataSource(jdbcConnectionDetails);


        } catch (Exception e) {
            actionContext.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
        }
        actionContext.setFinished();
    }
}
