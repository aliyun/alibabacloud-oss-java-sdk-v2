package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteVectorIndex operation.
 */
public final class DeleteVectorIndexRequest extends RequestModel {
    private final String bucket;
    private final String indexName;

    private DeleteVectorIndexRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.indexName = builder.indexName;
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
     * The name of the index to delete.
     */
    public String indexName() {
        return indexName;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String indexName;

        private Builder() {
            super();
        }

        private Builder(DeleteVectorIndexRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.indexName = request.indexName;
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
         * The name of the index to delete.
         */
        public Builder indexName(String value) {
            this.indexName = value;
            return this;
        }

        public DeleteVectorIndexRequest build() {
            return new DeleteVectorIndexRequest(this);
        }
    }
}

