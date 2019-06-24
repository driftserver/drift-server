package io.drift.plugin.jdbc;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.div;
import static io.drift.ui.infra.WicketUtil.label;

public class DBDeltaSummaryComponent extends Panel {

    public DBDeltaSummaryComponent(String id, String name, int insertCount, int updateCount, int deleteCount) {
        super(id);

        int totalCount = insertCount + updateCount + deleteCount;
        WebMarkupContainer changes, noChanges;

        add(label("name", name));

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
