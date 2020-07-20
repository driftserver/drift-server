package com.github.driftserver.filesystem;

import com.github.driftserver.core.metamodel.id.ModelId;

import java.io.Serializable;

public class FileSystemSnapshotId extends ModelId implements Serializable {

	public FileSystemSnapshotId() {
		super();
	}

	public FileSystemSnapshotId(String id) {
		super(id);
	}

}
