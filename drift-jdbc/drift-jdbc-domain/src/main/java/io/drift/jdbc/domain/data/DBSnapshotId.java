package io.drift.jdbc.domain.data;

import io.drift.core.metamodel.id.ModelId;

import java.io.Serializable;

public class DBSnapshotId extends ModelId implements Serializable {

	public DBSnapshotId() {
		super();
	}

	public DBSnapshotId(String id) {
		super(id);
	}

}
