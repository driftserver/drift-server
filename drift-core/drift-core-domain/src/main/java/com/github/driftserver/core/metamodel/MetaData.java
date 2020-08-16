package com.github.driftserver.core.metamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;

import java.time.LocalDateTime;

public class MetaData {

    private ModelId modelId;

    @JsonIgnore
    private LocalDateTime lastModifiedTimeStamp;

    @JsonIgnore
    private LocalDateTime createdTimeStamp;

    private String type;

    private ModelURN modelURN;

    public MetaData() {}

    public MetaData(ModelId modelId, LocalDateTime createdTimeStamp, LocalDateTime lastModifiedTimeStamp, String type, ModelURN modelURN) {
        this.modelId = modelId;
        this.createdTimeStamp = createdTimeStamp;
        this.lastModifiedTimeStamp = lastModifiedTimeStamp;
        this.type = type;
        this.modelURN = modelURN;
    }

    public ModelId getModelId() {
        return modelId;
    }

    public void setModelId(ModelId modelId) {
        this.modelId = modelId;
    }

    public LocalDateTime getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public LocalDateTime getLastModifiedTimeStamp() {
        return lastModifiedTimeStamp;
    }

    public void setLastModifiedTimeStamp(LocalDateTime timeStamp) {
        this.lastModifiedTimeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ModelURN getModelURN() {
        return modelURN;
    }

    public void setModelURN(ModelURN modelURN) {
        this.modelURN = modelURN;
    }

    static  public class Builder {
        private ModelId modelId;
        private LocalDateTime createdTimeStamp;
        private LocalDateTime lastUpdatedTimeStamp;
        private String type;
        private ModelURN modelURN;

        public Builder withModelId(ModelId modelId) {
            this.modelId = modelId;
            return this;
        }

        public Builder withLastUpdatedTimeStamp(LocalDateTime lastUpdatedTimeStamp) {
            this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
            return this;
        }

        public Builder withCreatedTimeStamp(LocalDateTime createdTimeStamp) {
            this.createdTimeStamp = createdTimeStamp;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withModelURN(ModelURN modelURN) {
            this.modelURN = modelURN;
            return this;
        }

        public MetaData build() {
            return new MetaData(modelId, createdTimeStamp, lastUpdatedTimeStamp, type, modelURN);
        }
    }

    static public Builder builder() { return new Builder(); }

}
