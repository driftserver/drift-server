package io.drift.plugin.jdbc.ui.app.flux.snapshot.viewpart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBSnapshotMainViewPart extends ViewPart implements Serializable {

    private List<TableViewPart> tables = new ArrayList<>();

    public void add(TableViewPart svTable) {
        tables.add(svTable);
    }

    public List<TableViewPart> getTables() {
        return tables;
    }

}
