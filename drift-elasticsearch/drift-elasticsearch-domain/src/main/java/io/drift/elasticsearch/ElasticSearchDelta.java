package io.drift.elasticsearch;

import io.drift.core.recording.SystemInteraction;
import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StorageId;

import java.io.Serializable;

public class ElasticSearchDelta extends SystemInteraction implements Storable, Serializable {

    @Override
    public StorageId getId() {
        return null;
    }
}
