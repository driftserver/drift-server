package io.drift.filesystem;

import io.drift.core.recording.SubSystemState;
import io.drift.core.recording.SystemInteraction;
import io.drift.core.store.storage.Storable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemSnapshot extends SystemInteraction implements Serializable, Storable, SubSystemState {

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
