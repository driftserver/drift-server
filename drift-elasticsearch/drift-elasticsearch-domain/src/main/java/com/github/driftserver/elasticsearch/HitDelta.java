package com.github.driftserver.elasticsearch;

import java.io.Serializable;

public class HitDelta implements Serializable {

    private Hit oldHit;
    private Hit newHit;

    public HitDelta() {}

    public HitDelta(Hit oldHit, Hit newHit) {
        this.oldHit = oldHit;
        this.newHit = newHit;
    }

    public Hit getOldHit() {
        return oldHit;
    }

    public void setOldHit(Hit oldHit) {
        this.oldHit = oldHit;
    }

    public Hit getNewHit() {
        return newHit;
    }

    public void setNewHit(Hit newHit) {
        this.newHit = newHit;
    }
}
