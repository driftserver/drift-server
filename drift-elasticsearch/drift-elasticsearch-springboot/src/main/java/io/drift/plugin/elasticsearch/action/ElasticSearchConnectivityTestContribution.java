package io.drift.plugin.elasticsearch.action;

import io.drift.core.recording.ProblemDescription;
import io.drift.core.system.SubSystemKey;
import io.drift.core.system.connectivity.SubSystemConnectivityActionContext;
import io.drift.core.systemdescription.SystemConnectivityTestContribution;
import io.drift.elasticsearch.ElasticSearchSettings;
import io.drift.plugin.elasticsearch.DriftElasticSearchAutoConfig;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static io.drift.plugin.elasticsearch.action.ElasticSearchExceptionWrapper.wrap;

@Component
public class ElasticSearchConnectivityTestContribution implements SystemConnectivityTestContribution {

    private final ElasticSearchConnectionManager connectionManager;

    public ElasticSearchConnectivityTestContribution(ElasticSearchConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public String getSubSystemType() {
        return DriftElasticSearchAutoConfig.ELASTICSEARCH_SUBSYSTEM_TYPE;
    }

    @Async
    @Override
    public void asyncTestConnectivity(SubSystemConnectivityActionContext actionContext) {

        SubSystemKey subSystemKey = actionContext.getSubSystem().getKey();
        String location = subSystemKey.getName();
        String action = null;

        try {

            action = "getting elasicsearch connection details";
            ElasticSearchSettings settings = (ElasticSearchSettings) actionContext.getConnectionDetails();

            action = "closing existing elasticsearch connections";
            connectionManager.stopConnection(subSystemKey);

            action = "testing elasticsearch connection";
            RestHighLevelClient client = connectionManager.getConnection(subSystemKey, settings);

            boolean pingResult = client.ping(RequestOptions.DEFAULT);

        } catch (Exception e) {
            actionContext.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
        }
        actionContext.setFinished();
    }
}
