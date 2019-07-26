package io.drift.plugin.jdbc.ui.app.flux.snapshot.graphmodel;

import java.io.Serializable;

public class TableRoot implements Serializable {

    private String name;
    private TableNode toNode;

    private String toNodeName;

    public TableRoot(String name, String tableName) {
        this.name = name;
        this.toNodeName = tableName;
    }

    public String getName() {
        return name;
    }

    public String getToNodeName() {
        return toNodeName;
    }

    public TableNode getToNode() {
        return toNode;
    }

    public void setToNode(TableNode toNode) {
        this.toNode = toNode;
    }
}
