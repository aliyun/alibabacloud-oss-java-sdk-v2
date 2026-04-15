package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class IcebergMetadata {

    @JsonProperty("schema")
    private IcebergSchema schema;

    @JsonProperty("partitionSpec")
    private IcebergPartitionSpec partitionSpec;

    @JsonProperty("writeOrder")
    private IcebergWriteOrder writeOrder;

    @JsonProperty("properties")
    private Map<String, String> properties;

    public IcebergMetadata() {
    }

    private IcebergMetadata(Builder builder) {
        this.schema = builder.schema;
        this.partitionSpec = builder.partitionSpec;
        this.writeOrder = builder.writeOrder;
        this.properties = builder.properties;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public IcebergSchema schema() {
        return schema;
    }

    public IcebergPartitionSpec partitionSpec() {
        return partitionSpec;
    }

    public IcebergWriteOrder writeOrder() {
        return writeOrder;
    }

    public Map<String, String> properties() {
        return properties;
    }

    public static class Builder {
        private IcebergSchema schema;
        private IcebergPartitionSpec partitionSpec;
        private IcebergWriteOrder writeOrder;
        private Map<String, String> properties;

        private Builder() {
        }

        private Builder(IcebergMetadata model) {
            this.schema = model.schema;
            this.partitionSpec = model.partitionSpec;
            this.writeOrder = model.writeOrder;
            this.properties = model.properties;
        }

        public Builder schema(IcebergSchema schema) {
            this.schema = schema;
            return this;
        }

        public Builder partitionSpec(IcebergPartitionSpec partitionSpec) {
            this.partitionSpec = partitionSpec;
            return this;
        }

        public Builder writeOrder(IcebergWriteOrder writeOrder) {
            this.writeOrder = writeOrder;
            return this;
        }

        public Builder properties(Map<String, String> properties) {
            this.properties = properties;
            return this;
        }

        public IcebergMetadata build() {
            return new IcebergMetadata(this);
        }
    }
}
