package io.drift.elasticsearch;

import java.util.ArrayList;
import java.util.List;

public class DummyElasticSearchSnapshotBuilder {


    public ElasticSearchSnapshot snapshot() {
        ElasticSearchSnapshot snapshot = new ElasticSearchSnapshot();

        {
            List<Hit> hits = new ArrayList<>();
            {
                Hit hit1 = new Hit();
                hit1.setContent("index 1 hit 1 content");
                hits.add(hit1);
            }
            {
                Hit hit2 = new Hit();
                hit2.setContent("index 1 hit 2 content");
                hits.add(hit2);
            }

            IndexSnapshot indexSnapshot = new IndexSnapshot();
            indexSnapshot.setHits(hits);
            snapshot.getIndexSnapshots().put("index1", indexSnapshot);
        }
        {
            List<Hit> hits = new ArrayList<>();
            {
                Hit hit1 = new Hit();
                hit1.setContent("index 2 hit 1 content");
                hits.add(hit1);
            }
            {
                Hit hit2 = new Hit();
                hit2.setContent("index 2 hit 2 content");
                hits.add(hit2);
            }

            IndexSnapshot indexSnapshot = new IndexSnapshot();
            indexSnapshot.setHits(hits);
            snapshot.getIndexSnapshots().put("index2", indexSnapshot);
        }

        return snapshot;
    }

}
