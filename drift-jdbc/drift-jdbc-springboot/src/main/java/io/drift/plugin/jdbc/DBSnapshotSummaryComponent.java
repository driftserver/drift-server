package io.drift.plugin.jdbc;

import io.drift.jdbc.domain.data.DBSnapShot;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.system.JDBCConnectionDetails;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;

public class DBSnapshotSummaryComponent extends Panel {

    public DBSnapshotSummaryComponent(String id, DBSnapShot dbSnapShot) {
        super(id);
        String name = dbSnapShot.getSubSystem();
        add(label("name", name));
    }
}