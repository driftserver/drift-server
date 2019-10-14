package io.drift.plugin.elasticsearch.action;

import io.drift.core.recording.SystemInteraction;
import io.drift.core.system.SubSystemKey;
import io.drift.elasticsearch.DummyElasticSearchSnapshotBuilder;
import io.drift.elasticsearch.ElasticSearchDelta;
import io.drift.elasticsearch.ElasticSearchSettings;
import io.drift.elasticsearch.ElasticSearchSnapshot;

public class ElasticSearchSession {

    private SubSystemKey subSystemKey;
    private ElasticSearchSettings elasticSearchSettings;

    public ElasticSearchSession(SubSystemKey subSystemKey, ElasticSearchSettings elasticSearchSettings) {
        this.subSystemKey = subSystemKey;
        this.elasticSearchSettings = elasticSearchSettings;
    }

    public SubSystemKey getSubSystemKey() {
        return subSystemKey;
    }

    public ElasticSearchSettings getElasticSearchSettings() {
        return elasticSearchSettings;
    }

    DummyElasticSearchSnapshotBuilder dummyElasticSearchSnapshotBuilder = new DummyElasticSearchSnapshotBuilder();

    public ElasticSearchSnapshot takeSnapshot() {
        return dummyElasticSearchSnapshotBuilder.snapshot();
    }


    public void init() {
    }

    public SystemInteraction getDelta() {
        return new ElasticSearchDelta();
    }

    public void destroy() {
    }
}
