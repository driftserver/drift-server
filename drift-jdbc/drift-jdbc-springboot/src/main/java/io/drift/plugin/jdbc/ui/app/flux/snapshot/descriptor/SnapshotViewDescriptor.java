package io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnapshotViewDescriptor {

    private List<RootDescriptor> roots = new ArrayList<>();

    private Map<String, TableViewDescriptor> tables = new HashMap<>();

    public List<RootDescriptor> getRoots() {
        return roots;
    }

    public void addRoot(RootDescriptor root) {
        roots.add(root);
    }

    public void addTable(TableViewDescriptor table) {
        tables.put(table.getTableName(), table);
    }

    public void connect() {
        for (RootDescriptor root : roots) {
            root.setTableDescriptor(tables.get(root.getTableName()));
        }
    }

}
