package io.drift.ui.app.flux.systemdescription;

import io.drift.core.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemStore {

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

    public List<SubSystemSettingsDTO> getEnvironmentSettings(String key) {
        return null;
                /*
                getSystemDescription().getConnectionDetails(getEnvironment(envName).getKey()).stream()
                .map(connDetails -> new SubSystemSettingsDTO(connDetails.getSystemDescription))
                .collect(Collectors.toList());

                 */

    }

    public Environment getEnvironment(String key) {
        return getSystemDescription().getEnvironments().stream()
                .filter(env -> env.getKey().getName().equals(key))
                .findFirst()
                .get();
    }
}
