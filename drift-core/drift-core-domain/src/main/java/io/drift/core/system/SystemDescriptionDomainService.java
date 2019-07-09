package io.drift.core.system;

import io.drift.core.system.connectivity.EnvironmentConnectivityActionContext;

public interface SystemDescriptionDomainService {

    SystemDescription getSystemDescription();

    EnvironmentConnectivityActionContext testConnectivity(EnvironmentKey environmentKey);

}
