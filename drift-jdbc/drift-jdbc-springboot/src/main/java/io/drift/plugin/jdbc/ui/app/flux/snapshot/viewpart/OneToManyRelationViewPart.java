package io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart;

import java.io.Serializable;

public class OneToManyRelationViewPart implements Serializable {
    private String name;
    private boolean active;

    public OneToManyRelationViewPart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
