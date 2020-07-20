package com.github.driftserver.core.system;

import com.github.driftserver.core.system.connectivity.EnvironmentConnectivityActionContext;

public interface SystemDescriptionDomainService {

    SystemDescription getSystemDescription();

    EnvironmentConnectivityActionContext testConnectivity(EnvironmentKey environmentKey);

}
