package io.drift.core.db.metadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "columns", "primaryKey" })
public class TableMetaData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, ColumnMetaData> columns = new HashMap<>();

	private String name;

	private PKMetaData primaryKey;

	protected TableMetaData() {
	}

	public TableMetaData(String name) {
		this.name = name;
	}

	public void add(ColumnMetaData columnMetaData) {
		columns.put(columnMetaData.getName(), columnMetaData);
	}

	public ColumnMetaData getColumn(String columnName) {
		return columns.get(columnName);
	}

	public Map<String, ColumnMetaData> getColumns() {
		return columns;
	}

	@JsonIgnore
	public List<ColumnMetaData> getColumnsInOrder() {
		return columns.values().stream().sorted(ColumnMetaData.COMPARE_BY_POSITION).collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public PKMetaData getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PKMetaData pkMetaData) {
		this.primaryKey = pkMetaData;
	}

}
