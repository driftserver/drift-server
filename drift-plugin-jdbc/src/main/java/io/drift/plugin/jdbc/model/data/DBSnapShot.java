package io.drift.plugin.jdbc.model.data;

import io.drift.core.api.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DBSnapShot implements Model {

	private Map<String, TableSnapShot> tables = new HashMap<>();

	public void add(TableSnapShot tableSnapShot) {
		tables.put(tableSnapShot.getTable(), tableSnapShot);
	}

	public TableSnapShot getTableSnapShotFor(String tableName) {
		return tables.get(tableName);
	}

	@JsonIgnore
	public Collection<TableSnapShot> getTableSnapShots() {
		return tables.values();
	}

}
