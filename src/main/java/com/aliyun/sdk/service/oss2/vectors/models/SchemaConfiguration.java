package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The schema configuration of a fusion vector index.
 */
public class SchemaConfiguration {
    @JsonProperty("fields")
    private List<FieldSchema> fields;

    public SchemaConfiguration() {
    }

    private SchemaConfiguration(Builder builder) {
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The schema of the fields. At least one field of the vector type is required.
     */
    public List<FieldSchema> fields() {
        return fields;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<FieldSchema> fields;

        private Builder() {
        }

        private Builder(SchemaConfiguration from) {
            this.fields = from.fields;
        }

        /**
         * The schema of the fields. At least one field of the vector type is required.
         */
        public Builder fields(List<FieldSchema> fields) {
            this.fields = fields;
            return this;
        }

        public SchemaConfiguration build() {
            return new SchemaConfiguration(this);
        }
    }
}
