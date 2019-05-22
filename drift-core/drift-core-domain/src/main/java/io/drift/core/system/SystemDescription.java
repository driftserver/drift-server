package io.drift.core.system;

import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StorageId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemDescription implements Storable {

    private Map<SubSystemEnvironmentKey, SubSystemConnectionDetails> connectionDetails = new HashMap<>();

    private List<Environment> environments = new ArrayList<>();

    private List<SubSystem> subSystems = new ArrayList<>();

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }

    public List<SubSystem> getSubSystems() {
        return subSystems;
    }

    public void setSubSystems(List<SubSystem> subSystems) {
        this.subSystems = subSystems;
    }

    public void addConnectionDetails(SubSystemKey subSystemKey, EnvironmentKey environmentKey, SubSystemConnectionDetails connectionDetails) {
        this.connectionDetails.put(new SubSystemEnvironmentKey(subSystemKey, environmentKey), connectionDetails);
    }

    public SubSystemConnectionDetails getConnectionDetails(SubSystemKey subSystemKey, EnvironmentKey environmentKey) {
        return connectionDetails.get(new SubSystemEnvironmentKey(subSystemKey, environmentKey));
    }

    public List<SubSystemConnectionDetails> getConnectionDetails(EnvironmentKey environmentKey) {
        return subSystems.stream()
                .map(subSystem -> getConnectionDetails(subSystem.getKey(), environmentKey))
                .collect(Collectors.toList());
    }

    private StorageId storageId;

    @Override
    public StorageId getId() {
        return storageId;
    }

    public Environment getEnvironment(EnvironmentKey environmentKey) {
        return environments.stream().filter(env -> env.getKey().equals(environmentKey)).findFirst().get();
    }

    public Map<SubSystemKey, SubSystemConnectionDetails> getConnectionDetails(EnvironmentKey environmentKey, String subsystemTypeName) {
        return getSubSystems().stream()
                .filter(subSystem -> subSystem.getType().equals(subsystemTypeName))
                .collect(Collectors.toMap(
                        subSystem -> subSystem.getKey(),
                        subSystem -> getConnectionDetails(subSystem.getKey(), environmentKey)));
    }
}
