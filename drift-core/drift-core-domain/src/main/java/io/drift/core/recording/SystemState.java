package io.drift.core.recording;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemState {

    private Map<String, SubSystemState> subSystems = new HashMap<>();

    public void addSubSystemState(String name, SubSystemState state) {
        subSystems.put(name, state);
    }

    public List<String> getSubSystemStates() {
        return new ArrayList<>(subSystems.keySet());
    }

    public SubSystemState getSubSystemState(String name) {
        return subSystems.get(name);
    }

}
