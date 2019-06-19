package io.drift.plugin.jdbc.ui.app.flux.snapshot.descriptor;

public class FKViewDescriptor {
    private String name;
    private String tableName;

    public FKViewDescriptor(String name, String tableName) {
        this.name = name;
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public String getTableName() {
        return tableName;
    }
}
