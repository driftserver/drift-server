package io.drift.filesystem;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class DirectorySnapshot extends FileSystemSnapshotItem implements Serializable {

    public DirectorySnapshot() {
    }

    public DirectorySnapshot(String path) {
        super(path);
    }
}
