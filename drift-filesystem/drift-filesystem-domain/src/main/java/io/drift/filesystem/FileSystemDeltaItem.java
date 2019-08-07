package io.drift.filesystem;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(value={})
public class FileSystemDeltaItem {

    private String path;

    private FileSystemDeltaType deltaType;


    public FileSystemDeltaItem() {
    }

    public FileSystemDeltaItem(String path, FileSystemDeltaType deltaType) {
        this.path = path;
        this.deltaType = deltaType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileSystemDeltaType getDeltaType() {
        return deltaType;
    }

    public void setDeltaType(FileSystemDeltaType deltaType) {
        this.deltaType = deltaType;
    }
}
