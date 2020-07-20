package io.drift.filesystem;

import io.drift.core.metamodel.id.ModelId;

import java.io.Serializable;

public class FileSystemSnapshotId extends ModelId implements Serializable {

	public FileSystemSnapshotId() {
		super();
	}

	public FileSystemSnapshotId(String id) {
		super(id);
	}

}
