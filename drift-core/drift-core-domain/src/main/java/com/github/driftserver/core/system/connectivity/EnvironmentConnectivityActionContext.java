package com.github.driftserver.core.system.connectivity;

import com.github.driftserver.core.system.*;

import java.util.*;

public class EnvironmentConnectivityActionContext {

    private Map<SubSystemKey, SubSystemConnectivityActionContext> subSystemContexts;

    private EnvironmentKey environmentKey;

    public EnvironmentConnectivityActionContext(SystemDescription systemDescription, EnvironmentKey environmentKey) {
        this.environmentKey = environmentKey;

        subSystemContexts = new HashMap<>();
        for(SubSystem subSystem: systemDescription.getSubSystems()) {
            SubSystemKey subSystemKey = subSystem.getKey();
            SubSystemConnectionDetails subSystemConnectionDetails = systemDescription.getConnectionDetails(subSystemKey, environmentKey);
            subSystemContexts.put(subSystemKey, new SubSystemConnectivityActionContext(subSystem, subSystemConnectionDetails));
        }

    }

    public EnvironmentKey getEnvironmentKey() {
        return environmentKey;
    }

    public Collection<SubSystemConnectivityActionContext> getSubSystemContexts() {
        return subSystemContexts.values();
    }

    public boolean isFinished(String subSystemKey) {
        return subSystemContexts.get(new SubSystemKey(subSystemKey)).isFinished();
    }

    public boolean hasProblems(String subSystemKey) {
        return subSystemContexts.get(new SubSystemKey(subSystemKey)).getActionLogger().hasProblems();
    }

    public boolean isFinished() {
        return subSystemContexts.values().stream().allMatch(ss -> ss.isFinished());
    }

    public SubSystemConnectivityActionContext getSubSystemContext(SubSystemKey subSystemKey) {
        return subSystemContexts.get(subSystemKey);
    }
}
