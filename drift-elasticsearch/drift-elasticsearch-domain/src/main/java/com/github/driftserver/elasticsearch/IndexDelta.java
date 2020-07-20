package com.github.driftserver.elasticsearch;

import java.util.ArrayList;
import java.util.List;

public class IndexDelta {

    private List<Hit> deletes = new ArrayList<>();

    private List<Hit> inserts = new ArrayList<>();

    private List<HitDelta> updates = new ArrayList<>();

    public void addDelete(Hit hit) {
        deletes.add(hit);
    }

    public void addInsert(Hit hit) {
        inserts.add(hit);
    }

    public void addUpdate(Hit hit, Hit lastHit) {
        updates.add(new HitDelta(hit, lastHit));
    }

    public List<Hit> getDeletes() {
        return deletes;
    }

    public List<Hit> getInserts() {
        return inserts;
    }

    public List<HitDelta> getUpdates() {
        return updates;
    }
}
