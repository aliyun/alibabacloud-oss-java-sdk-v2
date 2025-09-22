package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteVectors operation.
 */
public final class DeleteVectorsRequest extends RequestModel {
    private final String bucket;
    private final String indexName;
    private final List<String> keys;

    private DeleteVectorsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.indexName = builder.indexName;
        this.keys = builder.keys;
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
        return indexName;
    }

    /**
     * The list of vector keys to delete.
     */
    public List<String> keys() {
        return keys;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String indexName;
        private List<String> keys;

        private Builder() {
            super();
        }

        private Builder(DeleteVectorsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.indexName = request.indexName;
            this.keys = request.keys;
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
            this.indexName = value;
            return this;
        }

        /**
         * The list of vector keys to delete.
         */
        public Builder keys(List<String> value) {
            this.keys = value;
            return this;
        }

        public DeleteVectorsRequest build() {
            return new DeleteVectorsRequest(this);
        }
    }
}

