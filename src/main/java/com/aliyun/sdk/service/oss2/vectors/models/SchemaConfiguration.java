package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * The schema configuration of a fusion vector index.
 * <p>
 * The field definitions are kept as the raw JSON structure ({@link #fields()}), so attributes that
 * the service adds later do not require an SDK change. {@link #fieldSchemas()} exposes the same
 * definitions as the strongly-typed {@link FieldSchema} model when a typed view is more convenient.
 */
public class SchemaConfiguration {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final TypeReference<List<FieldSchema>> FIELD_SCHEMA_LIST =
            new TypeReference<List<FieldSchema>>() {
            };

    @JsonProperty("fields")
    private List<?> fields;

    public SchemaConfiguration() {
    }

    private SchemaConfiguration(Builder builder) {
        this.fields = builder.fields;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The schema of the fields, exactly as it is sent to or returned by the service. At least one
     * field of the vector type is required.
     * <p>
     * The elements are the raw JSON objects, so every attribute the service accepts is available
     * here, including the ones that have no strongly-typed model yet. Use {@link #fieldSchemas()}
     * when a typed view is more convenient.
     */
    public List<?> fields() {
        return fields;
    }

    /**
     * The schema of the fields as the strongly-typed {@link FieldSchema} model.
     * <p>
     * This is a convenience view over {@link #fields()}: it carries only the attributes that the SDK
     * models, so an attribute the service added and the SDK does not know yet is not visible here.
     * Use {@link #fields()} to access the complete definition.
     *
     * @return the typed view, or {@code null} when no field was set
     */
    public List<FieldSchema> fieldSchemas() {
        if (fields == null) {
            return null;
        }
        return JSON_MAPPER.convertValue(fields, FIELD_SCHEMA_LIST);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<?> fields;

        private Builder() {
        }

        private Builder(SchemaConfiguration from) {
            this.fields = from.fields;
        }

        /**
         * The schema of the fields. At least one field of the vector type is required.
         * <p>
         * The elements are the raw JSON objects: pass {@link FieldSchema} instances directly, or
         * {@link FieldSchema#toMap()} results when the typed model does not cover every attribute.
         */
        public Builder fields(List<?> fields) {
            this.fields = fields;
            return this;
        }

        public SchemaConfiguration build() {
            return new SchemaConfiguration(this);
        }
    }
}
