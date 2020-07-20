package com.github.driftserver.jdbc.domain.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.recording.model.SubSystemState;

public class DBSnapShot implements Serializable, Model, SubSystemState {

	private DBSnapshotId id;

	private Map<String, TableSnapShot> tables = new HashMap<>();

	public void add(TableSnapShot tableSnapShot) {
		tables.put(tableSnapShot.getTable(), tableSnapShot);
	}

	@Override
	public DBSnapshotId getId() {
		return id;
	}

	public TableSnapShot getTableSnapShotFor(String tableName) {
		return tables.get(tableName);
	}

	@JsonIgnore
	public Collection<TableSnapShot> getTableSnapShots() {
		return tables.values();
	}

	public void setId(DBSnapshotId id) {
		this.id = id;
	}

	private String subSystem;

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}


}
