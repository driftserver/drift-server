package com.github.driftserver.core.metamodel.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.driftserver.core.metamodel.id.ModelId;
import com.github.driftserver.core.metamodel.urn.ModelURN;

import java.time.LocalDateTime;
import java.util.Map;

public class MetaData {

    private ModelId modelId;

    @JsonIgnore
    private LocalDateTime timeStamp;

    private String type;

    private String description;

    private ModelURN modelURN;

    private Map<String, String> additionalMetaData;

    public MetaData() {}

    public MetaData(ModelId modelId, LocalDateTime timeStamp, String type, String description, ModelURN modelURN, Map<String, String> additionalMetaData) {
        this.modelId = modelId;
        this.timeStamp = timeStamp;
        this.type = type;
        this.description = description;
        this.modelURN = modelURN;
        this.additionalMetaData = additionalMetaData;
    }

    public ModelId getModelId() {
        return modelId;
    }

    public void setModelId(ModelId modelId) {
        this.modelId = modelId;
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

    public ModelURN getModelURN() {
        return modelURN;
    }

    public void setModelURN(ModelURN modelURN) {
        this.modelURN = modelURN;
    }

    public Map<String, String> getAdditionalMetaData() {
        return additionalMetaData;
    }

    public void setAdditionalMetaData(Map<String, String> additionalMetaData) {
        this.additionalMetaData = additionalMetaData;
    }

    static  public class Builder {
        private ModelId modelId;
        private LocalDateTime timeStamp;
        private String type;
        private String description;
        private ModelURN modelURN;
        private Map<String, String> additionalMetaData;

        public Builder withModelId(ModelId modelId) {
            this.modelId = modelId;
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

        public Builder withModelURN(ModelURN modelURN) {
            this.modelURN = modelURN;
            return this;
        }

        public Builder withAdditionalMetaData(Map<String, String> additionalMetaData) {
            this.additionalMetaData = additionalMetaData;
            return this;
        }

        public MetaData build() {
            return new MetaData(modelId, timeStamp, type, description, modelURN, additionalMetaData);
        }
    }

    static public Builder builder() { return new Builder(); }

}
