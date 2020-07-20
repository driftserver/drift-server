package io.drift.ui.app.flux.systemdescription;

import io.drift.core.system.*;
import io.drift.core.system.connectivity.EnvironmentConnectivityActionContext;
import io.drift.core.system.connectivity.SubSystemConnectivityActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SystemStore {

    private Map<EnvironmentKey, EnvironmentConnectivityActionContext> actionContexts = new HashMap<>();

    @Autowired
    SystemDescriptionDomainService domainService;

    public List<EnvironmentDTO> getEnvironments() {
        return getSystemDescription().getEnvironments().stream()
                .map(env -> new EnvironmentDTO(env.getKey().getName(), env.getName()))
                .collect(Collectors.toList());
    }

    public List<SubSystemDTO> getSubsystems() {
        return getSystemDescription().getSubSystems().stream()
                .map(subsystem -> new SubSystemDTO(subsystem.getKey().getName(), subsystem.getType(), subsystem.getName()))
                .collect(Collectors.toList());
    }

    public SystemDescription getSystemDescription() {
        return domainService.getSystemDescription();
    }

    public List<SubSystemSettingsDTO> getEnvironmentSettings(String envName) {
        return getSystemDescription()
                .getConnectionDetails(getEnvironment(envName).getKey())
                .entrySet().stream()
                .map(entry -> new SubSystemSettingsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Environment getEnvironment(String key) {
        return getSystemDescription().getEnvironments().stream()
                .filter(env -> env.getKey().getName().equals(key))
                .findFirst()
                .get();
    }

    public EnvironmentConnectivityActionContext getActionContext(String environmentKey) {
        return actionContexts.get(new EnvironmentKey(environmentKey));
    }


    public void store(EnvironmentConnectivityActionContext actionContext) {
        actionContexts.put(actionContext.getEnvironmentKey(), actionContext);
    }

    public SubSystemConnectivityActionContext getActionContext(String envKey, String subSystemKey) {
        EnvironmentConnectivityActionContext actionContext = getActionContext(envKey);
        if (actionContext == null) return null;
        return (actionContext.getSubSystemContext(new SubSystemKey(subSystemKey)));
    }
}
