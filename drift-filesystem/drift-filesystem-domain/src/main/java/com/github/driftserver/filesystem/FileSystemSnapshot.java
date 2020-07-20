package com.github.driftserver.filesystem;

import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.recording.model.SubSystemState;
import com.github.driftserver.core.recording.model.SystemInteraction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemSnapshot extends SystemInteraction implements Serializable, Model, SubSystemState {

    private FileSystemSnapshotId id;

    private Map<String, List<FileSystemSnapshotItem>> items = new HashMap<>();

    @Override
    public FileSystemSnapshotId getId() {
        return id;
    }

    public void setId(FileSystemSnapshotId id) {
        this.id = id;
    }


    public Map<String, List<FileSystemSnapshotItem>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<FileSystemSnapshotItem>> items) {
        this.items = items;
    }
}
