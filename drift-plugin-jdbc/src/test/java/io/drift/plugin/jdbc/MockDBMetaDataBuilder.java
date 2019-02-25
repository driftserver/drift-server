package io.drift.plugin.jdbc;

import io.drift.plugin.jdbc.model.metadata.ColumnMetaData;
import io.drift.plugin.jdbc.model.metadata.DBMetaData;
import io.drift.plugin.jdbc.model.metadata.TableMetaData;

import java.util.Random;

public class MockDBMetaDataBuilder {

	Random random = new Random();

	public DBMetaData createDbMetaData() {

		DBMetaData dbMetaData = new DBMetaData();

		for (int iTable = 1; iTable <= 1 + random.nextInt(5); iTable++) {
			TableMetaData tableMetaData = new TableMetaData("table_" + iTable);

			for (int iColumn = 0; iColumn < iTable; iColumn++) {
				ColumnMetaData columnMetaData = new ColumnMetaData("col_" + iTable + "_" + iColumn, "Strng", iColumn);
				tableMetaData.add(columnMetaData);
			}

			dbMetaData.add(tableMetaData);
		}

		return dbMetaData;

	}

}
