package io.drift.ui.app.flux.systemdescription;

import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SystemDescriptionDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemActions {

    @Autowired
    SystemDescriptionDomainService systemDescriptionDomainService;

    @Autowired
    SystemStore systemStore;

    public void asyncTestConnections(EnvironmentKey environmentKey) {
        systemStore.store(systemDescriptionDomainService.testConnectivity(environmentKey));
    }



}
