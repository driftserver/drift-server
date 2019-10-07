package io.drift.plugin.elasticsearch;

import io.drift.elasticsearch.ElasticSearchDelta;
import io.drift.elasticsearch.ElasticSearchSettings;
import org.apache.wicket.markup.html.panel.Panel;

public class ElasticSearchDeltaComponent extends Panel {

    public ElasticSearchDeltaComponent(String id, ElasticSearchDelta elasticSearchDelta, ElasticSearchSettings elasticSearchSettings) {
        super(id);
    }

}
