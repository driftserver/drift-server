package com.github.driftserver.elasticsearch;

import com.github.driftserver.core.metamodel.id.ModelId;

import java.io.Serializable;

public class ElasticSearchSnapshotId extends ModelId implements Serializable {

    public ElasticSearchSnapshotId() {
        super();
    }

    public ElasticSearchSnapshotId(String id) {
        super(id);
    }
}
