package com.github.driftserver.plugin.elasticsearch.action;

import com.github.driftserver.core.recording.model.SystemInteraction;
import com.github.driftserver.core.system.SubSystemKey;
import com.github.driftserver.elasticsearch.ElasticSearchDelta;
import com.github.driftserver.elasticsearch.ElasticSearchSettings;
import com.github.driftserver.elasticsearch.ElasticSearchSnapshot;
import com.github.driftserver.elasticsearch.ElasticsearchDeltaBuilder;
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
