package io.drift.plugin.jdbc;

import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;

public class DBDeltaSummaryComponent extends Panel {

    public DBDeltaSummaryComponent(String id, String name, int inserts, int updates, int deletes) {
        super(id);
        add(label("name", name));
        add(label("inserts", inserts));
        add(label("updates", updates));
        add(label("deletes", deletes));
    }

}
