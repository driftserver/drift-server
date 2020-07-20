package com.github.driftserver.filesystem;

import java.io.Serializable;

public class DirectoryDelta extends FileSystemDeltaItem implements Serializable {

    public DirectoryDelta() {
    }

    public DirectoryDelta(String path, FileSystemDeltaType deltaType) {
        super(path, deltaType);
    }

}
