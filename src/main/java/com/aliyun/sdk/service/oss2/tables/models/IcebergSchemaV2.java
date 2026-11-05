package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A table schema supporting struct, list, map, and their nested combinations.
 * Mutually exclusive with {@link IcebergSchema} in {@link IcebergMetadata} for table creation requests.
 */
public class IcebergSchemaV2 {

    @JsonProperty("type")
    private String type = "struct";

    @JsonProperty("schema-id")
    private Integer schemaId;

    @JsonProperty("identifier-field-ids")
    private List<Integer> identifierFieldIds;

    @JsonProperty("fields")
    private List<SchemaV2Field> fields;

    public IcebergSchemaV2() {
    }

    private IcebergSchemaV2(Builder builder) {
        this.schemaId = builder.schemaId;
        this.identifierFieldIds = builder.identifierFieldIds;
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String type() {
        return type;
    }

    public Integer schemaId() {
        return schemaId;
    }

    public List<Integer> identifierFieldIds() {
        return identifierFieldIds;
    }

    public List<SchemaV2Field> fields() {
        return fields;
    }

    public static class Builder {
        private Integer schemaId;
        private List<Integer> identifierFieldIds;
        private List<SchemaV2Field> fields;

        private Builder() {
        }

        private Builder(IcebergSchemaV2 model) {
            this.schemaId = model.schemaId;
            this.identifierFieldIds = model.identifierFieldIds;
            this.fields = model.fields;
        }

        public Builder schemaId(Integer schemaId) {
            this.schemaId = schemaId;
            return this;
        }

        public Builder identifierFieldIds(List<Integer> identifierFieldIds) {
            this.identifierFieldIds = identifierFieldIds;
            return this;
        }

        public Builder fields(List<SchemaV2Field> fields) {
            this.fields = fields;
            return this;
        }

        public IcebergSchemaV2 build() {
            return new IcebergSchemaV2(this);
        }
    }
}
