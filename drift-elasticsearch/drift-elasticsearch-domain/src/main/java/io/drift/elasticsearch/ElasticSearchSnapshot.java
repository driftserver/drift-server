package io.drift.elasticsearch;

import io.drift.core.metamodel.Model;
import io.drift.core.recording.model.SubSystemState;
import io.drift.core.recording.model.SystemInteraction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchSnapshot extends SystemInteraction implements Serializable, Model, SubSystemState {

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
