package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectors operation.
 */
public final class PutVectorsRequest extends VectorRequestModel {
    private final String bucket;

    private PutVectorsRequest(Builder builder) {
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
     * The name of the index.
     */
    public String indexName() {
        return (String)this.bodyFields.get("indexName");
    }

    /**
     * The vectors to insert.
     */
    public List<?> vectors() {
        return (List<?>)this.bodyFields.get("vectors");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(PutVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
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
         * The name of the index.
         */
        public Builder indexName(String value) {
            requireNonNull(value);
            this.bodyFields.put("indexName", value);
            return this;
        }

        /**
         * The vectors to insert.
         */
        public Builder vectors(List<?> value) {
            requireNonNull(value);
            this.bodyFields.put("vectors", value);
            return this;
        }

        public PutVectorsRequest build() {
            return new PutVectorsRequest(this);
        }
    }
}