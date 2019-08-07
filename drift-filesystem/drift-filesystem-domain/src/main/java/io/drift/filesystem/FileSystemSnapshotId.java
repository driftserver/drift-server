package io.drift.filesystem;

import io.drift.core.store.storage.StorageId;

import java.io.Serializable;

public class FileSystemSnapshotId extends StorageId implements Serializable {

	public FileSystemSnapshotId() {
		super();
	}

	public FileSystemSnapshotId(String id) {
		super(id);
	}

}
