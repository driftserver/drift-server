package io.drift.core.systemdescription;

import io.drift.core.ActionManager;
import io.drift.core.recording.ActionLogger;
import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.SystemDescription;
import io.drift.core.system.SystemDescriptionDomainService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SystemDescriptionDomainserviceImpl implements SystemDescriptionDomainService {

    private final SystemDescriptionStorage systemDescriptionStorage;
    private final List<SystemConnectivityTestContribution> contributions;
    private final ActionManager actionManager;

    public SystemDescriptionDomainserviceImpl(SystemDescriptionStorage systemDescriptionStorage, List<SystemConnectivityTestContribution> contributions, ActionManager actionManager) {
        this.systemDescriptionStorage = systemDescriptionStorage;
        this.contributions = contributions;
        this.actionManager = actionManager;
    }

    public SystemDescription getSystemDescription() {
            return systemDescriptionStorage.load();
    }

    @Override
    public UUID testConnectivity(EnvironmentKey environmentKey) {
        SystemDescription systemDescription = getSystemDescription();
        ActionLogger actionLogger = new ActionLogger(true);
        actionManager.register(actionLogger);
        contributions.forEach(contribution -> contribution.testConnectivity(environmentKey, systemDescription, actionLogger));
        return actionLogger.getId();
    }

    @Override
    public ActionLogger getConnectivityTestResult(UUID actionId) {
        return actionManager.get(actionId);
    }
}
