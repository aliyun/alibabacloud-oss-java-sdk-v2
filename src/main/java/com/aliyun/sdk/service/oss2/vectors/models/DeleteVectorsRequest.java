package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteVectors operation.
 */
public final class DeleteVectorsRequest extends VectorRequestModel {
    private final String bucket;

    private DeleteVectorsRequest(Builder builder) {
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
     * The list of vector keys to delete.
     */
    public List<String> keys() {
        return (List<String>)this.bodyFields.get("keys");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(DeleteVectorsRequest request) {
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
         * The list of vector keys to delete.
         */
        public Builder keys(List<String> value) {
            requireNonNull(value);
            this.bodyFields.put("keys", value);
            return this;
        }

        public DeleteVectorsRequest build() {
            return new DeleteVectorsRequest(this);
        }
    }
}