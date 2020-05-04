package io.drift.elasticsearch;

import io.drift.core.recording.SystemInteraction;
import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StorageId;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ElasticSearchDelta extends SystemInteraction implements Storable, Serializable {

    @Override
    public StorageId getId() {
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
