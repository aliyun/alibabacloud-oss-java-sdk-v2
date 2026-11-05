package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A struct type defining a group of named child fields.
 */
public class IcebergStructType implements IcebergComplexType {

    @JsonProperty("type")
    private String type = "struct";

    @JsonProperty("fields")
    private List<SchemaV2Field> fields;

    public IcebergStructType() {
    }

    private IcebergStructType(Builder builder) {
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String type() {
        return type;
    }

    public List<SchemaV2Field> fields() {
        return fields;
    }

    public static class Builder {
        private List<SchemaV2Field> fields;

        private Builder() {
        }

        private Builder(IcebergStructType model) {
            this.fields = model.fields;
        }

        public Builder fields(List<SchemaV2Field> fields) {
            this.fields = fields;
            return this;
        }

        public IcebergStructType build() {
            return new IcebergStructType(this);
        }
    }
}
