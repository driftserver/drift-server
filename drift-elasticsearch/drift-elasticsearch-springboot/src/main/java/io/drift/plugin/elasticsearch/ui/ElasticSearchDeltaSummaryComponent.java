package io.drift.plugin.elasticsearch.ui;

import io.drift.elasticsearch.ElasticSearchDelta;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.div;
import static io.drift.ui.infra.WicketUtil.label;

public class ElasticSearchDeltaSummaryComponent extends Panel {

    public ElasticSearchDeltaSummaryComponent(String id, ElasticSearchDelta elasticSearchDelta) {
        super(id);
        add(label("name", elasticSearchDelta.getSubSystem()));

        int insertCount = elasticSearchDelta.getInsertCount();
        int updateCount = elasticSearchDelta.getUpdateCount();
        int deleteCount = elasticSearchDelta.getDeleteCount();

        int totalCount = insertCount + updateCount + deleteCount;
        WebMarkupContainer changes, noChanges;

        add(changes = div("changes"));
        changes
                .add(label("inserts", insertCount))
                .add(label("updates", updateCount))
                .add(label("deletes", deleteCount));
        changes
                .setVisible(totalCount > 0);

        add(noChanges = div("noChanges"));
        noChanges
                .setVisible(totalCount == 0);

    }

}
