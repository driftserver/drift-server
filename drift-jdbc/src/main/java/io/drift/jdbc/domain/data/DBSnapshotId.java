package io.drift.jdbc.domain.data;

import io.drift.core.store.storage.StorageId;

public class DBSnapshotId extends StorageId {

	public DBSnapshotId() {
		super();
	}

	public DBSnapshotId(String id) {
		super(id);
	}

}
