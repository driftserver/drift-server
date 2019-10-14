package io.drift.plugin.elasticsearch.ui;

import io.drift.elasticsearch.ElasticSearchSnapshot;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;

public class ElasticSearchSnapshotSummaryComponent extends Panel {

    public ElasticSearchSnapshotSummaryComponent(String id, ElasticSearchSnapshot elasticSearchSnapshot) {
        super(id);
        add(label("name", elasticSearchSnapshot.getSubSystem()));
    }

}
