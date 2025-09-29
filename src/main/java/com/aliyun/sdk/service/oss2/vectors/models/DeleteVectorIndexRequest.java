package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteVectorIndex operation.
 */
public final class DeleteVectorIndexRequest extends RequestModel {
    private final String bucket;
    private final VectorIndexNameInfo vectorIndexNameInfo;

    private DeleteVectorIndexRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.vectorIndexNameInfo = builder.vectorIndexNameInfo;
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
        return vectorIndexNameInfo.indexName();
    }

    /**
     * The index name info.
     */
    public VectorIndexNameInfo vectorIndexNameInfo() {
        return vectorIndexNameInfo;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private VectorIndexNameInfo vectorIndexNameInfo;

        private Builder() {
            super();
            this.vectorIndexNameInfo = VectorIndexNameInfo.newBuilder().build();
        }

        private Builder(DeleteVectorIndexRequest from) {
            super(from);
            this.bucket = from.bucket;
            this.vectorIndexNameInfo = from.vectorIndexNameInfo;
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
            this.vectorIndexNameInfo = this.vectorIndexNameInfo.toBuilder().indexName(value).build();
            return this;
        }

        public Builder vectorIndexNameInfo(VectorIndexNameInfo value) {
            requireNonNull(value);
            this.vectorIndexNameInfo = value;
            return this;
        }

        public DeleteVectorIndexRequest build() {
            return new DeleteVectorIndexRequest(this);
        }
    }
}