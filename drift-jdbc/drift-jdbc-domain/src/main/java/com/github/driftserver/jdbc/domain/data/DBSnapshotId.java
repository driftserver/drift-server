package com.github.driftserver.jdbc.domain.data;

import com.github.driftserver.core.metamodel.id.ModelId;

import java.io.Serializable;

public class DBSnapshotId extends ModelId implements Serializable {

	public DBSnapshotId() {
		super();
	}

	public DBSnapshotId(String id) {
		super(id);
	}

}
