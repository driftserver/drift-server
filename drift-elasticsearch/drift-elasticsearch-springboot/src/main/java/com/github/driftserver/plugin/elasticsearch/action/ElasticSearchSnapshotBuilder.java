package com.github.driftserver.plugin.elasticsearch.action;

import com.github.driftserver.elasticsearch.ElasticSearchSettings;
import com.github.driftserver.elasticsearch.ElasticSearchSnapshot;
import com.github.driftserver.elasticsearch.Hit;
import com.github.driftserver.elasticsearch.IndexSnapshot;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ElasticSearchSnapshotBuilder {

    public ElasticSearchSnapshot createSnapshot(ElasticSearchSettings settings, RestHighLevelClient client) {
        ElasticSearchSnapshot snapshot = new ElasticSearchSnapshot();
        Map<String, IndexSnapshot> snapshots = new HashMap<>();
        snapshot.setIndexSnapshots(snapshots);


        for(String indexName: settings.getIndices()) {

            try {
                IndexSnapshot indexSnapshot = new IndexSnapshot();
                List<Hit> hits = new ArrayList<>();
                indexSnapshot.setHits(hits);
                snapshots.put(indexName, indexSnapshot);

                final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                SearchSourceBuilder source = new SearchSourceBuilder();
                source.query(QueryBuilders.matchAllQuery());

                SearchRequest searchRequest = new SearchRequest();
                searchRequest.indices(indexName);
                searchRequest.source(source);
                searchRequest.scroll(scroll);

                SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                String scrollId = searchResponse.getScrollId();
                SearchHit[] searchHits = searchResponse.getHits().getHits();

                while (searchHits != null && searchHits.length > 0) {
                    addHits(searchHits, hits);

                    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                    scrollRequest.scroll(scroll);
                    searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                    scrollId = searchResponse.getScrollId();
                    searchHits = searchResponse.getHits().getHits();
                }

                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
                clearScrollRequest.addScrollId(scrollId);
                ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
                boolean succeeded = clearScrollResponse.isSucceeded();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return snapshot;

    }

    private void addHits(SearchHit[] searchHits, List<Hit> hits) {
        hits.addAll(
                Arrays.stream(searchHits).
                        map(searchHit -> {
                            Hit hit = new Hit();
                            hit.setId(searchHit.getId());
                            hit.setContent(searchHit.getSourceAsString());
                            return hit;
                        })
                        .collect(Collectors.toList()));
    }

}
