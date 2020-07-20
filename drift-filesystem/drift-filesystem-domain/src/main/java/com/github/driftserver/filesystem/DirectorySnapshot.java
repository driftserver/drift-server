package com.github.driftserver.filesystem;

import java.io.Serializable;

public class DirectorySnapshot extends FileSystemSnapshotItem implements Serializable {

    public DirectorySnapshot() {
    }

    public DirectorySnapshot(String path) {
        super(path);
    }
}
