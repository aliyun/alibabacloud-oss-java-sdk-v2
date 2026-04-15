package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IcebergSchema {

    @JsonProperty("fields")
    private List<SchemaField> fields;

    public IcebergSchema() {
    }

    private IcebergSchema(Builder builder) {
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public List<SchemaField> fields() {
        return fields;
    }

    public static class Builder {
        private List<SchemaField> fields;

        private Builder() {
        }

        private Builder(IcebergSchema model) {
            this.fields = model.fields;
        }

        public Builder fields(List<SchemaField> fields) {
            this.fields = fields;
            return this;
        }

        public IcebergSchema build() {
            return new IcebergSchema(this);
        }
    }
}
