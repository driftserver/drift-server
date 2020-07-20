package com.github.driftserver.jdbc.ui.app.flux.snapshot.viewpart;

import com.github.driftserver.jdbc.domain.data.Row;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.ForeignKeyEdge;

import java.io.Serializable;

public class OneToManyRelationViewPart extends ViewPart implements Serializable {

    private Row row;
    private ForeignKeyEdge fkViewDescriptor;

    public OneToManyRelationViewPart(ForeignKeyEdge fkViewDescriptor, Row row) {
        this.fkViewDescriptor = fkViewDescriptor;
        this.row = row;
    }

    public ForeignKeyEdge getDescriptor() {
        return fkViewDescriptor;
    }

    public void select() {
        DBSnapshotMainViewPart mainViewPart = (DBSnapshotMainViewPart)getParent();
        mainViewPart.removeEdge(this);
    }

    public String getLabel() {
        return String.format("[%s -> %s]", getDescriptor().getFromNode().getShortDescription(row), getDescriptor().getName());
    }

    public String getPKValue() {
        return row.getValue("id");
    }
}
