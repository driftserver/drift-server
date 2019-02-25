package io.drift.plugin.jdbc.model.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DBMetaData implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> order = new ArrayList<>();

	private Map<String, TableMetaData> tables = new HashMap<>();

	public void add(TableMetaData tableMetaData) {
		tables.put(tableMetaData.getName(), tableMetaData);
	}

	public TableMetaData get(String tableName) {
		return tables.get(tableName);
	}

	public List<String> getOrder() {
		return order;
	}

	public Map<String, TableMetaData> getTables() {
		return tables;
	}

	@JsonIgnore
	public List<TableMetaData> getTablesInReverseOrder() {
		List<String> reverseOrder = new ArrayList<>(order);
		Collections.reverse(reverseOrder);
		return reverseOrder.stream().map(tableName -> get(tableName)).collect(Collectors.toList());
	}

	public void setOrder(List<String> order) {
		this.order = order;
	}
}
