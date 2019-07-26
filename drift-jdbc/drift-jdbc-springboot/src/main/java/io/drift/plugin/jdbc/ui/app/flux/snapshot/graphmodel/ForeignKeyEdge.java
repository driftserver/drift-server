package io.drift.plugin.jdbc.ui.app.flux.snapshot.graphmodel;

import java.io.Serializable;

public class ForeignKeyEdge implements Serializable {

    private String name;
    private String fromNodeName;
    private String toNodeName;
    private TableNode fromNode;
    private TableNode toNode;

    private String fkColumnName;

    public ForeignKeyEdge(String fromTableName, String toTableName, String name, String fkColumnName) {
        this.fromNodeName = fromTableName;
        this.toNodeName = toTableName;
        this.name = name;
        this.fkColumnName = fkColumnName;
    }

    public String getName() {
        return name;
    }

    public String getFromNodeName() {
        return fromNodeName;
    }

    public String getToNodeName() {
        return toNodeName;
    }

    public TableNode getFromNode() {
        return fromNode;
    }

    public void setFromNode(TableNode fromTable) {
        this.fromNode = fromTable;
    }

    public TableNode getToNode() {
        return toNode;
    }

    public void setToNode(TableNode toTable) {
        this.toNode = toTable;
    }

    public String getFkColumnName() {
        return fkColumnName;
    }

}
