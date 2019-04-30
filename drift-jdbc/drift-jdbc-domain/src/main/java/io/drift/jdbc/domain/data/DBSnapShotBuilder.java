package io.drift.jdbc.domain.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import io.drift.jdbc.domain.metadata.ColumnMetaData;
import io.drift.jdbc.domain.metadata.DBMetaData;
import io.drift.jdbc.domain.metadata.TableMetaData;

public class DBSnapShotBuilder {

	public DBSnapShot takeDBSnapShot(DataSource dataSource, DBMetaData dbMetaData, SnapshotConfig snapshotConfig) {
		DBSnapShot dbSnapShot = new DBSnapShot();
		for (String tableName : snapshotConfig.getTableNames()) {
			TableMetaData table = dbMetaData.get(tableName);
			dbSnapShot.add(takeTableSnapShot(table, dataSource));
		}
		return dbSnapShot;
	}

	private TableSnapShot takeTableSnapShot(TableMetaData table, DataSource dataSource) {
		try (Connection connection = dataSource.getConnection()){
			TableSnapShot tableSnapShot = new TableSnapShot(table.getName());
			ResultSet resultset = connection.createStatement().executeQuery("select * from " + table.getName());
			while (resultset.next()) {
				Row row = new Row();
				for (ColumnMetaData column : table.getColumnsInOrder()) {
					row.addValue(column.getName(), resultset.getString(column.getName()));
				}
				tableSnapShot.addRow(row);
			}
			return tableSnapShot;
		} catch (SQLException e) {
			throw new RuntimeException("could not take snapshot of table " + table.getName(), e);
		}
	}

}
