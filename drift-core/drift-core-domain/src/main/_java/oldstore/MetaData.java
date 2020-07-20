package io.drift.core.oldstore;

import io.drift.core.metamodel.id.ModelId;
import io.drift.core.metamodel.urn.ModelURN;

import java.time.LocalDateTime;
import java.util.Map;

public class MetaData {

    private ModelId storageId;

    private LocalDateTime timeStamp;

    private String type;

    private String description;

    private ModelURN path;

    private Map<String, String> additionalMetaData;

    public MetaData(ModelId storageId, LocalDateTime timeStamp, String type, String description, ModelURN path, Map<String, String> additionalMetaData) {
        this.storageId = storageId;
        this.timeStamp = timeStamp;
        this.type = type;
        this.description = description;
        this.path = path;
        this.additionalMetaData = additionalMetaData;
    }

    public ModelId getStorageId() {
        return storageId;
    }

    public void setStorageId(ModelId storageId) {
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

    public ModelURN getPath() {
        return path;
    }

    public void setPath(ModelURN path) {
        this.path = path;
    }

    public Map<String, String> getAdditionalMetaData() {
        return additionalMetaData;
    }

    public void setAdditionalMetaData(Map<String, String> additionalMetaData) {
        this.additionalMetaData = additionalMetaData;
    }

    static  public class Builder {
        private ModelId storageId;
        private LocalDateTime timeStamp;
        private String type;
        private String description;
        private ModelURN path;
        private Map<String, String> additionalMetaData;

        public Builder withStorageId(ModelId storageId) {
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

        public Builder withPath(ModelURN path) {
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
