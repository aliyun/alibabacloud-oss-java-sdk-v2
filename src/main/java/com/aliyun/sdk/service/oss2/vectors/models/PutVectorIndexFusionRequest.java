package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorIndexFusion operation.
 */
public final class PutVectorIndexFusionRequest extends VectorRequestModel {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String bucket;

    private PutVectorIndexFusionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The name of the index. It is unique in a vector bucket and 1 to 63 characters in length.
     */
    public String indexName() {
        return (String)this.bodyFields.get("indexName");
    }

    /**
     * The mode of the index. Valid value: fusion. Default value: fusion.
     */
    public String mode() {
        return (String)this.bodyFields.get("mode");
    }

    /**
     * The schema configuration of the index. It returns {@code null} when the schema was set from
     * a raw JSON string via {@link Builder#schemaConfiguration(String)}.
     */
    public SchemaConfiguration schemaConfiguration() {
        Object value = this.bodyFields.get("schemaConfiguration");
        return value instanceof SchemaConfiguration ? (SchemaConfiguration) value : null;
    }

    /**
     * Parses a raw JSON string into a Jackson tree so that it is serialized as a nested JSON
     * structure (instead of an escaped string literal), preserving any current or future fields
     * verbatim.
     */
    private static JsonNode parseJson(String json) {
        try {
            return JSON_MAPPER.readTree(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse the JSON string", e);
        }
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(PutVectorIndexFusionRequest from) {
            super(from);
            this.bucket = from.bucket;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The name of the index. It is unique in a vector bucket and 1 to 63 characters in length.
         */
        public Builder indexName(String value) {
            requireNonNull(value);
            this.bodyFields.put("indexName", value);
            return this;
        }

        /**
         * The mode of the index. Valid value: fusion. Default value: fusion.
         */
        public Builder mode(String value) {
            requireNonNull(value);
            this.bodyFields.put("mode", value);
            return this;
        }

        /**
         * Set the mode of the index using IndexModeType enum.
         */
        public Builder mode(IndexModeType value) {
            requireNonNull(value);
            this.bodyFields.put("mode", value.toString());
            return this;
        }

        /**
         * The schema configuration of the index.
         */
        public Builder schemaConfiguration(SchemaConfiguration value) {
            requireNonNull(value);
            this.bodyFields.put("schemaConfiguration", value);
            return this;
        }

        /**
         * Sets the schema configuration of the index from a raw JSON string. This is a flexible
         * overload for the nested schema structure: the JSON is passed through as-is, so any
         * current or future field parameters are supported without a matching strongly-typed
         * model. Use {@link #schemaConfiguration(SchemaConfiguration)} when you prefer the
         * compile-time-safe, strongly-typed builder.
         *
         * @param value the schemaConfiguration JSON string, for example
         *              {@code {"fields":[{"name":"embedding","type":"vector","dataType":"float32","dimension":4,"distanceMetric":"cosine"}]}}
         */
        public Builder schemaConfiguration(String value) {
            requireNonNull(value);
            this.bodyFields.put("schemaConfiguration", parseJson(value));
            return this;
        }

        public PutVectorIndexFusionRequest build() {
            return new PutVectorIndexFusionRequest(this);
        }
    }
}
