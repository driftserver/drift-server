package io.drift.ui.app.flux.systemdescription;

import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SystemDescriptionDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemActions {

    @Autowired
    SystemDescriptionDomainService systemDescriptionDomainService;

    public UUID testConnectivity(EnvironmentKey environmentKey) {
        return systemDescriptionDomainService.testConnectivity(environmentKey);
    }

}
