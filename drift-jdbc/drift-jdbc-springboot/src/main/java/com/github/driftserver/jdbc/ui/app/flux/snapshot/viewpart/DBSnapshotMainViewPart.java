package com.github.driftserver.jdbc.ui.app.flux.snapshot.viewpart;

import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.data.TableSnapShot;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.ForeignKeyEdge;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.TableNode;
import com.github.driftserver.jdbc.ui.app.flux.snapshot.graphmodel.TableRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBSnapshotMainViewPart extends ViewPart implements Serializable {

    public class Grid implements Serializable {

        private List<Row> rows = new ArrayList<>();

        Row addRow() {
            Row row = new Row();
            rows.add(row);
            return row;
        }

        public List<Row> getRows() {
            return rows;
        }
    }

    public class Row implements Serializable {
        private List<ViewPart> cells = new ArrayList<>();

        ViewPart getCell(int idx) {
            return cells.get(idx);
        }

        void addCell(ViewPart viewPart) {
            cells.add(viewPart);
        }

        public List<ViewPart> getCells() {
            return cells;
        }
    }

    private DBSnapShot dbSnapShot;

    private Grid grid = new Grid();


    public DBSnapshotMainViewPart(DBSnapShot dbSnapShot) {
        this.dbSnapShot = dbSnapShot;
    }

    public void add(ViewPart viewPart) {
        grid.addRow().addCell(viewPart);
        viewPart.setParent(this);
    }

    public void add(ViewPart... viewParts) {
        Row row = grid.addRow();
        for (ViewPart viewPart: viewParts) {
            row.addCell(viewPart);
            viewPart.setParent(this);
        }
    }

    public Grid getGrid() {
        return grid;
    }

    public void selectRoot(TableRoot rootQuery) {
        TableNode toNode = rootQuery.getToNode();
        TableSnapShot tableSnapShot = dbSnapShot.getTableSnapShotFor(toNode.getNodeName());
        add(new TableViewPart(tableSnapShot, toNode));
    }

    public void selectEdge(ForeignKeyEdge edge, com.github.driftserver.jdbc.domain.data.Row row) {
        TableSnapShot tableSnapShot = dbSnapShot.getTableSnapShotFor(edge.getToNodeName());
        TableNode toNode = edge.getToNode();

        OneToManyRelationViewPart relationViewPart = new OneToManyRelationViewPart(edge, row);
        TableViewPart tableViewPart = new TableViewPart(tableSnapShot, toNode);
        tableViewPart.setRelationViewPart(relationViewPart);

        add(relationViewPart, tableViewPart);
    }

    public void removeEdge(OneToManyRelationViewPart relationViewPart) {
        Row fkRow = grid.getRows().stream()
                .filter(row -> row.getCell(0).equals(relationViewPart))
                .findFirst()
                .get();
        grid.getRows().remove(fkRow);
    }

}
