package com.github.driftserver.elasticsearch;

import java.io.Serializable;
import java.util.List;

public class IndexSnapshot implements Serializable {

    private List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
