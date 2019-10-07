package io.drift.elasticsearch;

import io.drift.core.recording.SubSystemState;
import io.drift.core.recording.SystemInteraction;
import io.drift.core.store.storage.Storable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchSnapshot extends SystemInteraction implements Serializable, Storable, SubSystemState {

    private ElasticSearchSnapshotId id;

    @Override
    public ElasticSearchSnapshotId getId() {
        return id;
    }


    private Map<String, IndexSnapshot> indexSnapshots = new HashMap<>();

    public void setId(ElasticSearchSnapshotId id) {
        this.id = id;
    }


    public Map<String, IndexSnapshot> getIndexSnapshots() {
        return indexSnapshots;
    }

    public void setIndexSnapshots(Map<String, IndexSnapshot> indexSnapshots) {
        this.indexSnapshots = indexSnapshots;
    }

}
