package io.drift.elasticsearch;

import java.util.Map;

public class ElasticsearchDeltaBuilder {

    public ElasticSearchDelta createElasticSearchDelta(ElasticSearchSnapshot oldElasticSearchSnapShot, ElasticSearchSnapshot newElasticSearchSnapShot) {
        if (oldElasticSearchSnapShot == null)
            return createElasticSearchDelta(newElasticSearchSnapShot);
        ElasticSearchDelta elasticSearchDelta = new ElasticSearchDelta();
        for (Map.Entry<String, IndexSnapshot> entry : newElasticSearchSnapShot.getIndexSnapshots().entrySet()) {
            String indexName = entry.getKey();
            IndexSnapshot newIndexSnapShot = entry.getValue();
            IndexSnapshot oldIndexSnapshot = oldElasticSearchSnapShot.getIndexSnapshots().get(indexName);
            IndexDelta indexDelta = createIndexDelta(newIndexSnapShot, oldIndexSnapshot);
            elasticSearchDelta.add(indexName, indexDelta);
        }
        return elasticSearchDelta;
    }

    private IndexDelta createIndexDelta(IndexSnapshot newIndexSnapShot, IndexSnapshot oldIndexSnapshot) {
        IndexDelta indexDelta = new IndexDelta();
        newIndexSnapShot.getHits().stream().forEach(newHit -> {
            Hit oldHit = findHitWithSameId(newHit.getId(), oldIndexSnapshot);
            if (oldHit==null) {
                indexDelta.addInsert(newHit);
            } else if (!compareHitByValue(newHit, oldHit)) {
                indexDelta.addUpdate(newHit, oldHit);
            }
        });
        oldIndexSnapshot.getHits().stream().forEach(oldHit -> {
            Hit newHit = findHitWithSameId(oldHit.getId(), newIndexSnapShot);
            if (newHit == null) {
                indexDelta.addDelete(oldHit);
            }
        });
        return indexDelta;

    }

    private boolean compareHitByValue(Hit hit, Hit oldHit) {
        return hit.getContent().equals(oldHit.getContent());
    }

    private Hit findHitWithSameId(String key, IndexSnapshot indexSnapshot) {
        return indexSnapshot.getHits().stream()
                .filter(hit -> hit.getId().equals(key))
                .findFirst()
                .orElseGet(null);
    }

    private ElasticSearchDelta createElasticSearchDelta(ElasticSearchSnapshot elasticSearchSnapShot) {
        ElasticSearchDelta elasticSearchDelta = new ElasticSearchDelta();
        for (Map.Entry<String, IndexSnapshot> entry : elasticSearchSnapShot.getIndexSnapshots().entrySet()) {
            String indexName = entry.getKey();
            IndexSnapshot indexSnapShot = entry.getValue();
            IndexDelta indexDelta = createIndexDelta(indexSnapShot);
            elasticSearchDelta.add(indexName, indexDelta);

        }
        return elasticSearchDelta;
    }

    private IndexDelta createIndexDelta(IndexSnapshot indexSnapShot) {
        IndexDelta indexDelta = new IndexDelta();
        indexSnapShot.getHits().stream().forEach(hit -> {
            indexDelta.addInsert(hit);
        });
        return indexDelta;
    }

}
