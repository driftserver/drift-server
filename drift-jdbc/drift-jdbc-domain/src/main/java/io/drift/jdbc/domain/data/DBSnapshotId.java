package io.drift.jdbc.domain.data;

import io.drift.core.store.storage.StorageId;

import java.io.Serializable;

public class DBSnapshotId extends StorageId implements Serializable {

	public DBSnapshotId() {
		super();
	}

	public DBSnapshotId(String id) {
		super(id);
	}

}
