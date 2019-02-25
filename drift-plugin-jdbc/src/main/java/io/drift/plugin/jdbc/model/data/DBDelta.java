package io.drift.plugin.jdbc.model.data;

import io.drift.core.api.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DBDelta implements Serializable, Model {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private DeltaSummary summary;

	private Map<String, TableDelta> tableDeltas = new HashMap<>();

	public void add(TableDelta tableDelta) {
		tableDeltas.put(tableDelta.getTable(), tableDelta);
	}

	public TableDelta get(String tableName) {
		return tableDeltas.get(tableName);
	}

	public DeltaSummary getSummary() {
		if (summary == null) {
			summary = new DeltaSummary();
			for (TableDelta tableDelta : tableDeltas.values()) {
				summary.inserts += tableDelta.getInserts().size();
				summary.updates += tableDelta.getUpdates().size();
				summary.deletes += tableDelta.getDeletes().size();
			}
		}
		return summary;
	}

	public Map<String, TableDelta> getTableDeltas() {
		return tableDeltas;
	}

}
