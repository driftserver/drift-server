package io.drift.ui.app.flux.systemdescription;

import java.io.Serializable;

public class SubSystemDTO implements Serializable {

    private String name;

    public SubSystemDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
