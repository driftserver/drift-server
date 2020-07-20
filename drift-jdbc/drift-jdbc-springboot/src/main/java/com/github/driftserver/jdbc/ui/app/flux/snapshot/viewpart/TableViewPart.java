package com.github.driftserver.jdbc.ui.app.flux.snapshot.viewpart;

import com.github.driftserver.jdbc.domain.data.Row;
import com.github.driftserver.jdbc.domain.data.TableSnapShot;
import com.github.driftserver.jdbc.domain.metadata.ColumnMetaData;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.ForeignKeyEdge;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.TableNode;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class TableViewPart extends ViewPart implements Serializable {

    private TableSnapShot snapShot;
    private TableNode tableViewDescriptor;
    private OneToManyRelationViewPart relationViewPart;

    public TableViewPart(TableSnapShot snapShot, TableNode tableViewDescriptor) {
        this.snapShot = snapShot;
        this.tableViewDescriptor = tableViewDescriptor;
    }

    public void setRelationViewPart(OneToManyRelationViewPart relationViewPart) {
        this.relationViewPart = relationViewPart;
    }

    public String getName() {
        return tableViewDescriptor.getNodeName();
    }

    public List<ForeignKeyEdge> getRelations() {
        return tableViewDescriptor.getEdges();
    }

    public void selectRelation(ForeignKeyEdge fkViewDescriptor, Row row) {
        DBSnapshotMainViewPart mainViewPart = (DBSnapshotMainViewPart) getParent();
        mainViewPart.selectEdge(fkViewDescriptor, row);

    }

    public List<ColumnMetaData> getColumns() {
        return tableViewDescriptor.getColumns();
    }

    public List<Row> getRows() {
        if (relationViewPart == null)
            return snapShot.getRows();

        String fkColumnName = relationViewPart.getDescriptor().getFkColumnName();
        String pkValue = relationViewPart.getPKValue();

        return snapShot.getRows().stream()
                .filter(row -> {
                    String fkValue = row.getValue(fkColumnName);
                    return fkValue.equals(pkValue);
                })
                .collect(Collectors.toList());

    }
}
