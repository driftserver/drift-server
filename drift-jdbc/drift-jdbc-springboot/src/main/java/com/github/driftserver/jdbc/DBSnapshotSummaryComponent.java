package com.github.driftserver.jdbc;

import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.label;

public class DBSnapshotSummaryComponent extends Panel {

    public DBSnapshotSummaryComponent(String id, DBSnapShot dbSnapShot) {
        super(id);
        String name = dbSnapShot.getSubSystem();
        add(label("name", name));
    }
}