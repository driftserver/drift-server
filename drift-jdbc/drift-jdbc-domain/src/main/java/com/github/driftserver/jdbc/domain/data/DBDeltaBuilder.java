package com.github.driftserver.jdbc.domain.data;

import com.github.driftserver.jdbc.domain.metadata.ColumnMetaData;
import com.github.driftserver.jdbc.domain.metadata.DBMetaData;
import com.github.driftserver.jdbc.domain.metadata.PKMetaData;
import com.github.driftserver.jdbc.domain.metadata.TableMetaData;

public class DBDeltaBuilder {

	private DBMetaData dbMetaData;

	public DBDeltaBuilder(DBMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}

	private boolean compareByPK(PKMetaData pk, Row row1, Row row2) {
		for (ColumnMetaData column : pk.getColumns()) {
			if (!row1.getValue(column.getName()).equals(row2.getValue(column.getName())))
				return false;
		}
		return true;
	}

	private boolean compareRowByValues(TableMetaData table, Row row1, Row row2) {
		for (ColumnMetaData column : table.getColumnsInOrder()) {
			Object value1 = row1.getValue(column.getName());
			Object value2 = row2.getValue(column.getName());
			if (value1 == null)
				value1 = Row.Null;
			if (value2 == null)
				value2 = Row.Null;
			if (!value1.equals(value2))
				return false;
		}
		return true;
	}

	public DBDelta createDBDelta(DBSnapShot newDBSnapShot) {
		DBDelta dbDelta = new DBDelta();
		for (TableSnapShot newTableSnapShot : newDBSnapShot.getTableSnapShots()) {
			TableDelta tableDelta = createTableDelta(newTableSnapShot);
			dbDelta.add(tableDelta);
		}
		return dbDelta;
	}

	public DBDelta createDBDelta(DBSnapShot oldDBSnapShot, DBSnapShot newDBSnapShot) {
		if (oldDBSnapShot == null)
			return createDBDelta(newDBSnapShot);
		DBDelta dbDelta = new DBDelta();
		for (TableSnapShot newTableSnapShot : newDBSnapShot.getTableSnapShots()) {
			TableMetaData tableMetaData = dbMetaData.get(newTableSnapShot.getTable());
			TableSnapShot oldTableSnapshot = oldDBSnapShot.getTableSnapShotFor(newTableSnapShot.getTable());
			TableDelta tableDelta = createTableDelta(tableMetaData, newTableSnapShot, oldTableSnapshot);
			dbDelta.add(tableDelta);
		}
		return dbDelta;
	}

	private TableDelta createTableDelta(TableMetaData tableMetaData, TableSnapShot newTableSnapShot, TableSnapShot oldTableSnapshot) {
		TableDelta delta = new TableDelta(tableMetaData.getName());
		for (Row newRow : newTableSnapShot.getRows()) {
			Row oldRow = findRowWithSameKeyAs(oldTableSnapshot, newRow, tableMetaData);
			if (oldRow == null) {
				delta.addInsert(newRow);
			} else if (!compareRowByValues(tableMetaData, newRow, oldRow)) {
				delta.addUpdate(newRow, oldRow);
			}
		}
		for (Row oldRow : oldTableSnapshot.getRows()) {
			Row newRow = findRowWithSameKeyAs(newTableSnapShot, oldRow, tableMetaData);
			if (newRow == null) {
				delta.addDelete(oldRow);
			}
		}
		return delta;
	}

	private TableDelta createTableDelta(TableSnapShot tableSnapShot) {
		TableDelta delta = new TableDelta(tableSnapShot.getTable());
		for (Row newRow : tableSnapShot.getRows()) {
			delta.addInsert(newRow);
		}
		return delta;
	}

	private Row findRowWithSameKeyAs(TableSnapShot tableSnapshot, Row row, TableMetaData tableMetaData) {
		if (tableMetaData.getPrimaryKey()==null || tableMetaData.getPrimaryKey().getColumns()==null || tableMetaData.getPrimaryKey().getColumns().size() == 0 ) {
			return tableSnapshot.getRows().stream().filter(snapshotRow -> compareRowByValues(tableMetaData, row, snapshotRow)).findAny().orElse(null);
		}
		return tableSnapshot.getRows().stream().filter(lastRow -> compareByPK(tableMetaData.getPrimaryKey(), row, lastRow)).findAny().orElse(null);
	}

}
