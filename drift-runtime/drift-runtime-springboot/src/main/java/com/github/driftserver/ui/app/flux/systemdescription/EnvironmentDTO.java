package com.github.driftserver.ui.app.flux.systemdescription;

import java.io.Serializable;

public class EnvironmentDTO implements Serializable {

    private String key;

    private String displayName;

    EnvironmentDTO(String key, String displayName) {
        this.key = key;
        this.displayName = displayName;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

}
