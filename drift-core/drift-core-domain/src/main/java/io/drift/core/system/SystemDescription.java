package io.drift.core.system;

import io.drift.core.metamodel.Model;
import io.drift.core.metamodel.id.ModelId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemDescription implements Model {

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
        SubSystemEnvironmentKey subSystemEnvironmentKey = new SubSystemEnvironmentKey(subSystemKey, environmentKey);
        SubSystemConnectionDetails subSystemConnectionDetails = connectionDetails.get(subSystemEnvironmentKey);
        return subSystemConnectionDetails == null ? new NullDetails() : subSystemConnectionDetails;
    }

    public Map<SubSystemKey, SubSystemConnectionDetails> getConnectionDetails(EnvironmentKey environmentKey) {
        return subSystems.stream()
                .collect(Collectors.toMap(
                        subSystem -> subSystem.getKey(),
                        subSystem -> getConnectionDetails(subSystem.getKey(), environmentKey)));
    }

    private ModelId storageId;

    public ModelId getId() {
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

    public SubSystem getSubSystem(SubSystemKey subSystemKey) {
        return subSystems.stream().filter(subSystem -> subSystem.getKey().equals(subSystemKey)).findFirst().get();

    }
}
