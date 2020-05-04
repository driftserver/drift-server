package io.drift.plugin.elasticsearch.action;

import io.drift.core.recording.SystemInteraction;
import io.drift.core.system.SubSystemKey;
import io.drift.elasticsearch.ElasticSearchDelta;
import io.drift.elasticsearch.ElasticSearchSettings;
import io.drift.elasticsearch.ElasticSearchSnapshot;
import io.drift.elasticsearch.ElasticsearchDeltaBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticSearchSession {

    private SubSystemKey subSystemKey;
    private ElasticSearchSettings settings;
    private ElasticSearchConnectionManager connectionManager;

    private ElasticSearchSnapshot lastSnapShot;
    private ElasticSearchDelta delta;

    public ElasticSearchSession(SubSystemKey subSystemKey, ElasticSearchSettings settings, ElasticSearchConnectionManager connectionManager) {
        this.subSystemKey = subSystemKey;
        this.settings = settings;
        this.connectionManager = connectionManager;
    }

    public SubSystemKey getSubSystemKey() {
        return subSystemKey;
    }

    public ElasticSearchSettings getElasticSearchSettings() {
        return settings;
    }

    public ElasticSearchSnapshot takeSnapshot() {
        RestHighLevelClient client = connectionManager.getConnection(subSystemKey, settings);
        ElasticSearchSnapshot snapShot = new ElasticSearchSnapshotBuilder().createSnapshot(settings, client);
        snapShot.setSubSystem(subSystemKey.getName());

        if (lastSnapShot != null) {
            ElasticsearchDeltaBuilder deltaBuilder = new ElasticsearchDeltaBuilder();
            delta = deltaBuilder.createElasticSearchDelta(lastSnapShot, snapShot);
            delta.setSubSystem(subSystemKey.getName());
        }
        lastSnapShot = snapShot;
        return snapShot;
    }



    public void init() {
    }

    public SystemInteraction getDelta() {
        return delta;
    }

    public void destroy() {
    }
}
