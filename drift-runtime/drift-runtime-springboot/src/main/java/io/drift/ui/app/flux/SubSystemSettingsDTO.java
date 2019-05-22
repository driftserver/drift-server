package io.drift.ui.app.flux;

import java.io.Serializable;

public class SubSystemSettingsDTO implements Serializable {

    private String name;

    public SubSystemSettingsDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
