package com.github.driftserver.core.systemdescription;

import com.github.driftserver.core.system.EnvironmentKey;
import com.github.driftserver.core.system.SystemDescription;
import com.github.driftserver.core.system.SystemDescriptionDomainService;
import com.github.driftserver.core.system.connectivity.EnvironmentConnectivityActionContext;
import com.github.driftserver.core.system.connectivity.SubSystemConnectivityActionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SystemDescriptionDomainserviceImpl implements SystemDescriptionDomainService {

    private final SystemDescriptionStorage systemDescriptionStorage;
    private final List<SystemConnectivityTestContribution> contributions;

    public SystemDescriptionDomainserviceImpl(SystemDescriptionStorage systemDescriptionStorage, List<SystemConnectivityTestContribution> contributions) {
        this.systemDescriptionStorage = systemDescriptionStorage;
        this.contributions = contributions;
    }

    public SystemDescription getSystemDescription() {
            return systemDescriptionStorage.load();
    }


    private Map<String, SystemConnectivityTestContribution> contributionMap = null;

    private SystemConnectivityTestContribution getContribution(String subSystemType) {
        if (contributionMap==null) {
            initContributionMap();
        }
        return contributionMap.get(subSystemType);
    }

    private void initContributionMap() {
        Map<String, SystemConnectivityTestContribution> contributionMap = new HashMap<>();
        for(SystemConnectivityTestContribution contribution: contributions) {
            contributionMap.put(contribution.getSubSystemType(), contribution);
        }
        this.contributionMap = contributionMap;
    }

    @Override
    public EnvironmentConnectivityActionContext testConnectivity(EnvironmentKey environmentKey) {
        SystemDescription systemDescription = getSystemDescription();
        EnvironmentConnectivityActionContext actionContext = new EnvironmentConnectivityActionContext(systemDescription, environmentKey);
        for(SubSystemConnectivityActionContext subSystemContext: actionContext.getSubSystemContexts()) {
            getContribution(subSystemContext.getSubSystem().getType()).asyncTestConnectivity(subSystemContext);
        }
        return actionContext;
    }

}
