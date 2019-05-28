package io.drift.core.recording;

import java.util.HashMap;
import java.util.Map;

public class SystemState {

    private Map<String, SubSystemState> subSystems = new HashMap<>();

    public void addSubSystemState(String name, SubSystemState state) {
        subSystems.put(name, state);
    }



}
