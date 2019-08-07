package io.drift.filesystem;

import java.io.Serializable;

public class FileSnapshot extends FileSystemSnapshotItem implements Serializable  {

    public FileSnapshot() {
    }

    public FileSnapshot(String path) {
        super(path);
    }
}
