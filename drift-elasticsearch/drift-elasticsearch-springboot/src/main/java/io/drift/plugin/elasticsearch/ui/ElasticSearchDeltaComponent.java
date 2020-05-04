package io.drift.plugin.elasticsearch.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.rowset.internal.Row;
import io.drift.elasticsearch.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.List;

import static io.drift.ui.infra.WicketUtil.label;
import static io.drift.ui.infra.WicketUtil.listView;

public class ElasticSearchDeltaComponent extends Panel {


    public class ElasticSearchIndexDeltaComponent extends Fragment {
        public ElasticSearchIndexDeltaComponent(String id, IndexDelta indexDelta) {
            super(id, "indexDeltaFragment", ElasticSearchDeltaComponent.this);
        }
    }

    public ElasticSearchDeltaComponent(String id, ElasticSearchDelta elasticSearchDelta, ElasticSearchSettings elasticSearchSettings) {
        super(id);
        add(listView("indices", () -> elasticSearchSettings.getIndices(), indexItem -> {
            String indexName = indexItem.getModelObject();
            IndexDelta indexDelta = elasticSearchDelta.getIndexDelta(indexName);
            indexItem.add(label("indexName", indexName));
            indexItem.add(hitListView("inserts", indexDelta.getInserts()));
            indexItem.add(hitDeltaListView("updates", indexDelta.getUpdates()));
            indexItem.add(hitListView("deletes", indexDelta.getDeletes()));

        }));
    }

    private ListView<Hit> hitListView(String id, List<Hit> hits) {
        return listView(id, hits, hitItem -> {
            Hit hit = hitItem.getModelObject();
            hitItem.add(new Label("content", pretty(hit.getContent())));
        });
    }

    private ListView<HitDelta> hitDeltaListView(String id, List<HitDelta> hitDeltas) {
        return listView(id, hitDeltas, hitDeltaItem -> {
            HitDelta hitDelta = hitDeltaItem.getModelObject();
            hitDeltaItem.add(new Label("oldContent", hitDelta.getOldHit().getContent()));
            hitDeltaItem.add(new Label("newContent", hitDelta.getNewHit().getContent()));
        });
    }

    private String pretty(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(jsonString, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
