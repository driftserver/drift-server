package io.drift.core.db.metadata;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DBMetaDataBuilder {

	private void buildColumns(DatabaseMetaData jdbcMetaData, String tableName, TableMetaData tableMetaData) throws SQLException {
		ResultSet columns = jdbcMetaData.getColumns(null, null, tableName, null);
		while (columns.next()) {
			String columnName = columns.getString("COLUMN_NAME");
			int ordinalPosition = columns.getInt("ORDINAL_POSITION");
			String datatype = columns.getString("DATA_TYPE");
			tableMetaData.add(new ColumnMetaData(columnName, datatype, ordinalPosition));
		}
	}

	public DBMetaData buildDBMetaData(DatabaseMetaData jdbcMetaData, String... tableNames) {
		DBMetaData dbMetaData = new DBMetaData();
		for (String tableName : tableNames) {
			dbMetaData.getOrder().add(tableName);
			dbMetaData.add(buildtTable(jdbcMetaData, tableName));
		}
		return dbMetaData;
	}

	public DBMetaData buildDBMetaData(DataSource dataSource, String... tableNames) {
		DatabaseMetaData jdbcMetaData = getJdbcMetaData(dataSource);
		return buildDBMetaData(jdbcMetaData, tableNames);
	}

	private PKMetaData buildPK(DatabaseMetaData jdbcMetaData, String tableName, TableMetaData tableMetaData) throws SQLException {
		ResultSet columns = jdbcMetaData.getPrimaryKeys(null, null, tableName);
		PKMetaData pk = new PKMetaData();
		while (columns.next()) {
			String columnName = columns.getString("COLUMN_NAME");
			pk.addColumn(tableMetaData.getColumn(columnName));
		}
		return pk;
	}

	public TableMetaData buildtTable(DatabaseMetaData jdbcMetaData, String tableName) {
		try {
			TableMetaData tableMetaData = new TableMetaData(tableName);
			buildColumns(jdbcMetaData, tableName, tableMetaData);
			tableMetaData.setPrimaryKey(buildPK(jdbcMetaData, tableName, tableMetaData));
			return tableMetaData;
		} catch (SQLException e) {
			throw new RuntimeException("could not get metadata for table " + tableName, e);
		}
	}

	private DatabaseMetaData getJdbcMetaData(DataSource dataSource) {
		try {
			return dataSource.getConnection().getMetaData();
		} catch (SQLException e) {
			throw new RuntimeException("could not get metadata from jdbc datasource", e);
		}
	}

}
