package com.aliyun.sdk.service.oss2.vectors.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectorIndex operation.
 */
public final class GetVectorIndexRequest extends VectorRequestModel {
    private final String bucket;

    private GetVectorIndexRequest(Builder builder) {
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
     * The name of the vector index.
     */
    public String indexName() {
        return (String)this.bodyFields.get("indexName");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(GetVectorIndexRequest from) {
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
         * The name of the vector index.
         */
        public Builder indexName(String value) {
            requireNonNull(value);
            this.bodyFields.put("indexName", value);
            return this;
        }

        public GetVectorIndexRequest build() {
            return new GetVectorIndexRequest(this);
        }
    }
}