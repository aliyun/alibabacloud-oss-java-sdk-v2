package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.VectorIndexNameInfoJson;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectorIndex operation.
 */
public final class GetVectorIndexRequest extends RequestModel {
    private final String bucket;
    private final VectorIndexNameInfoJson vectorIndexNameInfoJson;

    private GetVectorIndexRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.vectorIndexNameInfoJson = builder.vectorIndexNameInfoJson;
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
     * The request body schema.
     */
    public VectorIndexNameInfoJson vectorIndexNameInfoJson() {
        return vectorIndexNameInfoJson;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private VectorIndexNameInfoJson vectorIndexNameInfoJson;

        private Builder() {
            super();
            this.vectorIndexNameInfoJson = new VectorIndexNameInfoJson();
        }

        private Builder(GetVectorIndexRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.vectorIndexNameInfoJson = request.vectorIndexNameInfoJson;
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
            this.vectorIndexNameInfoJson.indexName = value;
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder vectorIndexNameInfoJson(VectorIndexNameInfoJson vectorIndexNameInfoJson) {
            requireNonNull(vectorIndexNameInfoJson);
            this.vectorIndexNameInfoJson = vectorIndexNameInfoJson;
            return this;
        }

        public GetVectorIndexRequest build() {
            return new GetVectorIndexRequest(this);
        }
    }
}
