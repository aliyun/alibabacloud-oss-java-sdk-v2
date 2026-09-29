package com.aliyun.sdk.service.oss2.vectors.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorIndexFusion operation.
 */
public final class PutVectorIndexFusionRequest extends VectorRequestModel {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
     * The schema configuration of the index. It returns {@code null} only when no schema was set,
     * whichever way it was provided.
     */
    public SchemaConfiguration schemaConfiguration() {
        Object value = this.bodyFields.get("schemaConfiguration");
        return value instanceof SchemaConfiguration ? (SchemaConfiguration) value : null;
    }

    /**
     * Parses a raw JSON string into a {@link SchemaConfiguration}. The schema keeps the field
     * definitions as the raw JSON structure, so any current or future attribute is preserved
     * verbatim without a matching strongly-typed model.
     */
    private static SchemaConfiguration parseSchema(String json) {
        try {
            return JSON_MAPPER.readValue(json, SchemaConfiguration.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse the schemaConfiguration JSON string", e);
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
         * overload for the nested schema structure: the JSON is parsed into a
         * {@link SchemaConfiguration}, which keeps the field definitions as the raw JSON structure,
         * so any current or future field parameters are supported without a matching
         * strongly-typed model. Use {@link #schemaConfiguration(SchemaConfiguration)} when you
         * prefer the compile-time-safe, strongly-typed builder.
         *
         * @param value the schemaConfiguration JSON string, for example
         *              {@code {"fields":[{"name":"embedding","type":"vector","dataType":"float32","dimension":4,"distanceMetric":"cosine"}]}}
         */
        public Builder schemaConfiguration(String value) {
            requireNonNull(value);
            this.bodyFields.put("schemaConfiguration", parseSchema(value));
            return this;
        }

        public PutVectorIndexFusionRequest build() {
            return new PutVectorIndexFusionRequest(this);
        }
    }
}
