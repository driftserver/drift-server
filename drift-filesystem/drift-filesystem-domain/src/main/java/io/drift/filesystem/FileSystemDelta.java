package io.drift.filesystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.drift.core.metamodel.Model;
import io.drift.core.metamodel.id.ModelId;
import io.drift.core.recording.model.SystemInteraction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemDelta extends SystemInteraction implements Model, Serializable {

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
    public ModelId getId() {
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
