package com.github.driftserver.core.system.connectivity;

import com.github.driftserver.core.infra.logging.ActionLogger;
import com.github.driftserver.core.system.SubSystemConnectionDetails;
import com.github.driftserver.core.system.SubSystem;

public class SubSystemConnectivityActionContext {

    private boolean finished = false;

    private SubSystem subSystem;

    private SubSystemConnectionDetails connectionDetails;

    private ActionLogger actionLogger;


    public SubSystemConnectivityActionContext(SubSystem subSystem, SubSystemConnectionDetails connectionDetails) {
        this.subSystem = subSystem;
        this.connectionDetails = connectionDetails;
        actionLogger = new ActionLogger(true);
    }

    public SubSystem getSubSystem() {
        return subSystem;
    }

    public SubSystemConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

    public ActionLogger getActionLogger() {
        return actionLogger;
    }

    public void setFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

}
