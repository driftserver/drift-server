package com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel;

import com.github.driftserver.jdbc.domain.data.Row;
import com.github.driftserver.jdbc.domain.metadata.ColumnMetaData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableNode implements Serializable {

    private List<ColumnMetaData> columns = new ArrayList<>();

    private List<ForeignKeyEdge> edges = new ArrayList<>();

    private String nodeName;

    public TableNode(String tableName) {
        this.nodeName = tableName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public List<ForeignKeyEdge> getEdges() {
        return edges;
    }

    public void addEdge(ForeignKeyEdge edge) {
        edges.add(edge);
    }

    public List<ColumnMetaData> getColumns() {
        return columns;
    }

    public String getShortDescription(Row row) {
        return row.getValue("name");
    }
}
