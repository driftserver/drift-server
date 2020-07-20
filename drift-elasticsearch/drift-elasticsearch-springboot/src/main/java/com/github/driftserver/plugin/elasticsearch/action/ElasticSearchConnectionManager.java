package com.github.driftserver.plugin.elasticsearch.action;

import com.github.driftserver.core.system.SubSystemKey;
import com.github.driftserver.elasticsearch.ElasticSearchSettings;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ElasticSearchConnectionManager {

    private Map<SubSystemKey, RestHighLevelClient> clients = new HashMap<>();

    public void stopConnection(SubSystemKey subSystemKey) {
        RestHighLevelClient client = clients.get(subSystemKey);
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clients.remove(subSystemKey);
        }
    }

    public RestHighLevelClient getConnection(SubSystemKey subSystemKey, ElasticSearchSettings settings) {
        RestHighLevelClient client = clients.get(subSystemKey);
        if (client == null) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(settings.getUserName(), settings.getPassword()));
            client = new RestHighLevelClient(
                    RestClient
                            .builder(new HttpHost(settings.getUrl(), 9200))
                            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
            );
            clients.put(subSystemKey, client);
        }
        return client;
    }
}
