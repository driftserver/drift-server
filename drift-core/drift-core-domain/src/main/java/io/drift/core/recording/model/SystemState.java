package io.drift.core.recording.model;

import java.util.*;
import java.util.stream.Collectors;

public class SystemState {

    private Map<String, SubSystemState> subSystems = new HashMap<>();

    public void addSubSystemState(String name, SubSystemState state) {
        subSystems.put(name, state);
    }

    private List<String> orderedSubSystemKeys;

    public List<String> getOrderedSubSystemKeys() {
        if (orderedSubSystemKeys==null) orderedSubSystemKeys = subSystems.keySet().stream().sorted().collect(Collectors.toList());
        return orderedSubSystemKeys;
    }

    public SubSystemState getSubSystemState(String subSystemKey) {
        return subSystems.get(subSystemKey);
    }

}
