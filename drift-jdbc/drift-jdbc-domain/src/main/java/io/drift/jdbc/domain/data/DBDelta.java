package io.drift.jdbc.domain.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.drift.core.recording.SystemInteraction;
import io.drift.core.store.storage.Storable;

public class DBDelta extends SystemInteraction implements Serializable, Storable {

	private static final long serialVersionUID = 1L;

	private DBDeltaId id;

	@JsonIgnore
	private DeltaSummary summary;

	private Map<String, TableDelta> tableDeltas = new HashMap<>();

	public void add(TableDelta tableDelta) {
		tableDeltas.put(tableDelta.getTable(), tableDelta);
	}

	public TableDelta get(String tableName) {
		return tableDeltas.get(tableName);
	}

	@Override
	public DBDeltaId getId() {
		return id;
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

	public void setId(DBDeltaId id) {
		this.id = id;
	}

}
