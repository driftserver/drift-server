package io.drift.plugin.elasticsearch.action;

import io.drift.core.system.SubSystemKey;
import io.drift.elasticsearch.ElasticSearchSettings;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.*;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Component
public class ElasticSearchConnectionManager {

    private Map<SubSystemKey, RestHighLevelClient> clients = new HashMap<>();

    public void stopConnection(ElasticSearchSettings settings) {

    }

    public RestHighLevelClient getConnection(SubSystemKey subSystemKey, ElasticSearchSettings settings) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(settings.getUserName(), settings.getPassword()));
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient
                        .builder(new HttpHost(settings.getUrl(), 9200))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );
        clients.put(subSystemKey, client);
        return client;
    }
}
