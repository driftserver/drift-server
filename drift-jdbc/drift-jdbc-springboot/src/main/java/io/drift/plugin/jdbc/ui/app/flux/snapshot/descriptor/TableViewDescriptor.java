package io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor;

import java.util.ArrayList;
import java.util.List;

public class TableViewDescriptor {

    private List<FKViewDescriptor> foreignKeys = new ArrayList<>();

    private String tableName;

    public TableViewDescriptor(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<FKViewDescriptor> getForeignKeys() {
        return foreignKeys;
    }

    public void addFk(FKViewDescriptor fkViewDescriptor) {
        foreignKeys.add(fkViewDescriptor);
    }
}
