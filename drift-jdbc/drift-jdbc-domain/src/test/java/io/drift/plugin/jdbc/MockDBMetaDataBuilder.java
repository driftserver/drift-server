package io.drift.plugin.jdbc;

import java.util.Random;

import io.drift.jdbc.domain.metadata.ColumnMetaData;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.TableMetaData;

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
