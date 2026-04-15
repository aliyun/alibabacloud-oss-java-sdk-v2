package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableMetadata {

    @JsonProperty("iceberg")
    private IcebergMetadata iceberg;

    public TableMetadata() {
    }

    private TableMetadata(Builder builder) {
        this.iceberg = builder.iceberg;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public IcebergMetadata iceberg() {
        return iceberg;
    }

    public static class Builder {
        private IcebergMetadata iceberg;

        private Builder() {
        }

        private Builder(TableMetadata model) {
            this.iceberg = model.iceberg;
        }

        public Builder iceberg(IcebergMetadata iceberg) {
            this.iceberg = iceberg;
            return this;
        }

        public TableMetadata build() {
            return new TableMetadata(this);
        }
    }
}
