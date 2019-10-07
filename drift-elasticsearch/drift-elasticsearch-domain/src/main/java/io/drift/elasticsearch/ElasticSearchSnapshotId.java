package io.drift.elasticsearch;

import io.drift.core.store.storage.StorageId;

import java.io.Serializable;

public class ElasticSearchSnapshotId extends StorageId implements Serializable {

    public ElasticSearchSnapshotId() {
        super();
    }

    public ElasticSearchSnapshotId(String id) {
        super(id);
    }
}
