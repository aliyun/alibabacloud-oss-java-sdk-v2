package com.aliyun.sdk.service.oss2.vectors.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorIndexFusion operation.
 */
public final class PutVectorIndexFusionRequest extends VectorRequestModel {

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

        public PutVectorIndexFusionRequest build() {
            return new PutVectorIndexFusionRequest(this);
        }
    }
}
