package com.github.driftserver.core.systemdescription;

import com.github.driftserver.core.system.connectivity.SubSystemConnectivityActionContext;
import org.springframework.scheduling.annotation.Async;

public interface SystemConnectivityTestContribution {

    String getSubSystemType();

    @Async
    void asyncTestConnectivity(SubSystemConnectivityActionContext subSystemContext);
}
