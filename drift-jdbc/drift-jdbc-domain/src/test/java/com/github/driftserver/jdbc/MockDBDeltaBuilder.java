package com.github.driftserver.jdbc;

import java.util.Random;

import com.github.driftserver.jdbc.domain.data.DBDelta;
import com.github.driftserver.jdbc.domain.data.DBSnapShot;
import com.github.driftserver.jdbc.domain.data.Row;
import com.github.driftserver.jdbc.domain.data.TableDelta;
import com.github.driftserver.jdbc.domain.data.TableSnapShot;
import com.github.driftserver.jdbc.domain.metadata.ColumnMetaData;
import com.github.driftserver.jdbc.domain.metadata.DBMetaData;
import com.github.driftserver.jdbc.domain.metadata.TableMetaData;

public class MockDBDeltaBuilder {

	Random random = new Random();

	public DBDelta createDbDelta(DBMetaData dbMetaData) {

		DBDelta dbDelta = new DBDelta();

		int iTable = 0;
		for (TableMetaData tableMetaData : dbMetaData.getTables().values()) {
			TableDelta tableDelta = new TableDelta(tableMetaData.getName());

			for (int iRow = 0; iRow < random.nextInt(20); iRow++) {
				Row row = new Row();
				for (ColumnMetaData columnMetaData : tableMetaData.getColumnsInOrder()) {
					row.addValue(columnMetaData.getName(), "v_" + iTable + "_" + iRow + "_" + columnMetaData.getOrdinalPosition());

					if (iRow % 3 == 0)
						tableDelta.addInsert(row);
					if (iRow % 3 == 1)
						tableDelta.addUpdate(row, row);
					if (iRow % 3 == 2)
						tableDelta.addDelete(row);

				}
			}
			dbDelta.add(tableDelta);
			iTable++;
		}

		return dbDelta;

	}

	public DBSnapShot createDBSnapshot(DBMetaData dbMetaData) {
		DBSnapShot dbSnapShot = new DBSnapShot();

		int iTable = 0;
		for (TableMetaData table : dbMetaData.getTables().values()) {
			TableSnapShot tableSnapShot = new TableSnapShot(table.getName());

			for (int iRow = 0; iRow < random.nextInt(20); iRow++) {
				Row row = new Row();
				for (ColumnMetaData column : table.getColumnsInOrder()) {
					row.addValue(column.getName(), "v_" + iTable + "_" + iRow + "_" + column.getOrdinalPosition());
				}
				tableSnapShot.addRow(row);
			}
			dbSnapShot.add(tableSnapShot);
		}

		return dbSnapShot;
	}

}
