package io.drift.elasticsearch;

import io.drift.core.metamodel.Model;
import io.drift.core.metamodel.id.ModelId;
import io.drift.core.recording.model.SystemInteraction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchDelta extends SystemInteraction implements Model, Serializable {

    @Override
    public ModelId getId() {
        return null;
    }

    private Map<String, IndexDelta> deltas = new HashMap<>();

    public void add(String indexName, IndexDelta indexDelta) {
        deltas.put(indexName, indexDelta);
    }

    public IndexDelta getIndexDelta(String indexName) {
        return deltas.get(indexName);
    }

    public int getInsertCount() {
        return deltas.values().stream()
                .mapToInt(indexDelta -> indexDelta.getInserts().size())
                .sum();
    }

    public int getUpdateCount() {
        return deltas.values().stream()
                .mapToInt(indexDelta -> indexDelta.getUpdates().size())
                .sum();
    }

    public int getDeleteCount() {
        return deltas.values().stream()
                .mapToInt(indexDelta -> indexDelta.getDeletes().size())
                .sum();
    }
}
