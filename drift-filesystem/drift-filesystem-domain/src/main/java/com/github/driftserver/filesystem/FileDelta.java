package com.github.driftserver.filesystem;

import java.io.Serializable;

public class FileDelta extends FileSystemDeltaItem implements Serializable {

    public FileDelta() {
    }

    public FileDelta(String path, FileSystemDeltaType deltaType) {
        super(path, deltaType);
    }

}
