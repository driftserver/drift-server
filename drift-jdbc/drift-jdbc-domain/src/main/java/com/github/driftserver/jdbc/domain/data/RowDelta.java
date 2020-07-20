package com.github.driftserver.jdbc.domain.data;

import java.io.Serializable;

public class RowDelta implements Serializable {

    private Row oldRow;
    private Row newRow;

    public RowDelta() {
    }

    public RowDelta(Row oldRow, Row newRow) {
        this.oldRow = oldRow;
        this.newRow = newRow;
    }

    public Row getOldRow() {
        return oldRow;
    }

    public void setOldRow(Row oldRow) {
        this.oldRow = oldRow;
    }

    public Row getNewRow() {
        return newRow;
    }

    public void setNewRow(Row newRow) {
        this.newRow = newRow;
    }
}
