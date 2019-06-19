package io.drift.ui.app.flux.systemdescription;

import java.io.Serializable;

public class SubSystemDTO implements Serializable {

    private String key;
    private String type;
    private String description;

    public SubSystemDTO(String key, String type, String description) {
        this.key = key;
        this.type = type;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
