package com.github.driftserver.core.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MapSettings implements SubSystemConnectionDetails, Serializable {

    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
