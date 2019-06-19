package io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor;

public class RootDescriptor {

    private TableViewDescriptor tableDescriptor;

    private String name;

    private String tableName;

    public RootDescriptor(String name, String tableName) {
        this.name = name;
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public String getTableName() {
        return tableName;
    }

    public TableViewDescriptor getTableDescriptor() {
        return tableDescriptor;
    }

    public void setTableDescriptor(TableViewDescriptor tableDescriptor) {
        this.tableDescriptor = tableDescriptor;
    }
}
