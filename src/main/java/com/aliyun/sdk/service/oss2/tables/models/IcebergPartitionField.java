package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcebergPartitionField {

    @JsonProperty("sourceId")
    private Integer sourceId;

    @JsonProperty("transform")
    private String transform;

    @JsonProperty("fieldId")
    private Integer fieldId;

    @JsonProperty("name")
    private String name;

    public IcebergPartitionField() {
    }

    private IcebergPartitionField(Builder builder) {
        this.sourceId = builder.sourceId;
        this.transform = builder.transform;
        this.fieldId = builder.fieldId;
        this.name = builder.name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer sourceId() {
        return sourceId;
    }

    public String transform() {
        return transform;
    }

    public Integer fieldId() {
        return fieldId;
    }

    public String name() {
        return name;
    }

    public static class Builder {
        private Integer sourceId;
        private String transform;
        private Integer fieldId;
        private String name;

        private Builder() {
        }

        private Builder(IcebergPartitionField model) {
            this.sourceId = model.sourceId;
            this.transform = model.transform;
            this.fieldId = model.fieldId;
            this.name = model.name;
        }

        public Builder sourceId(Integer sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder transform(String transform) {
            this.transform = transform;
            return this;
        }

        public Builder fieldId(Integer fieldId) {
            this.fieldId = fieldId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public IcebergPartitionField build() {
            return new IcebergPartitionField(this);
        }
    }
}