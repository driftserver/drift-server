package io.drift.filesystem;

import java.io.Serializable;

public class DirectorySettings implements Serializable {

    private String path;

    public DirectorySettings() {}

    public DirectorySettings(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
