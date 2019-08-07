package io.drift.filesystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.drift.core.recording.SystemInteraction;
import io.drift.core.store.storage.Storable;
import io.drift.core.store.storage.StorageId;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemDelta extends SystemInteraction implements Storable, Serializable {

    @JsonIgnore
    private FileSystemDeltaSummary summary;

    private Map<String, List<FileSystemDeltaItem>> items = new HashMap<>();

    public Map<String, List<FileSystemDeltaItem>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<FileSystemDeltaItem>> items) {
        this.items = items;
    }

    @Override
    public StorageId getId() {
        return null;
    }

    public FileSystemDeltaSummary getSummary() {
        if (summary == null) {
            summary = new FileSystemDeltaSummary();
            for (List<FileSystemDeltaItem> itemList:  items.values()) {
                summary.count += itemList.size();
            }
        }
        return summary;
    }

}
