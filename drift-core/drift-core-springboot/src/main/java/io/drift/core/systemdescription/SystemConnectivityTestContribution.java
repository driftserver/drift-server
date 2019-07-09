package io.drift.core.systemdescription;

import io.drift.core.system.connectivity.SubSystemConnectivityActionContext;
import org.springframework.scheduling.annotation.Async;

public interface SystemConnectivityTestContribution {

    String getSubSystemType();

    @Async
    void asyncTestConnectivity(SubSystemConnectivityActionContext subSystemContext);
}
