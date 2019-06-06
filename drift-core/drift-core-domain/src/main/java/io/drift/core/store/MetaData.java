package io.drift.core.store;

import io.drift.core.store.storage.StorageId;
import io.drift.core.store.storage.StoragePath;

import java.time.LocalDateTime;
import java.util.Map;

public class MetaData {

    private StorageId storageId;

    private LocalDateTime timeStamp;

    private String type;

    private String description;

    private StoragePath path;

    private Map<String, String> additionalMetaData;

    public MetaData(StorageId storageId, LocalDateTime timeStamp, String type, String description, StoragePath path, Map<String, String> additionalMetaData) {
        this.storageId = storageId;
        this.timeStamp = timeStamp;
        this.type = type;
        this.description = description;
        this.path = path;
        this.additionalMetaData = additionalMetaData;
    }

    public StorageId getStorageId() {
        return storageId;
    }

    public void setStorageId(StorageId storageId) {
        this.storageId = storageId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StoragePath getPath() {
        return path;
    }

    public void setPath(StoragePath path) {
        this.path = path;
    }

    public Map<String, String> getAdditionalMetaData() {
        return additionalMetaData;
    }

    public void setAdditionalMetaData(Map<String, String> additionalMetaData) {
        this.additionalMetaData = additionalMetaData;
    }

    static  public class Builder {
        private StorageId storageId;
        private LocalDateTime timeStamp;
        private String type;
        private String description;
        private StoragePath path;
        private Map<String, String> additionalMetaData;

        public Builder withStorageId(StorageId storageId) {
            this.storageId = storageId;
            return this;
        }

        public Builder withTimeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPath(StoragePath path) {
            this.path = path;
            return this;
        }

        public Builder withAdditionalMetaData(Map<String, String> additionalMetaData) {
            this.additionalMetaData = additionalMetaData;
            return this;
        }

        public MetaData build() {
            return new MetaData(storageId, timeStamp, type, description, path, additionalMetaData);
        }
    }

    static public Builder builder() { return new Builder(); }

}
