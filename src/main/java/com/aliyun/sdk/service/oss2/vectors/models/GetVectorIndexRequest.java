package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorIndexRequestJson;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectorIndex operation.
 */
public final class GetVectorIndexRequest extends RequestModel {
    private final String bucket;
    private final GetVectorIndexRequestJson getVectorIndexRequestJson;

    private GetVectorIndexRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.getVectorIndexRequestJson = builder.getVectorIndexRequestJson;
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
    public GetVectorIndexRequestJson getVectorIndexRequestJson() {
        return getVectorIndexRequestJson;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private GetVectorIndexRequestJson getVectorIndexRequestJson;

        private Builder() {
            super();
            this.getVectorIndexRequestJson = new GetVectorIndexRequestJson();
        }

        private Builder(GetVectorIndexRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.getVectorIndexRequestJson = request.getVectorIndexRequestJson;
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
            this.getVectorIndexRequestJson.indexName = value;
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder getVectorIndexRequestJson(GetVectorIndexRequestJson getVectorIndexRequestJson) {
            requireNonNull(getVectorIndexRequestJson);
            this.getVectorIndexRequestJson = getVectorIndexRequestJson;
            return this;
        }

        public GetVectorIndexRequest build() {
            return new GetVectorIndexRequest(this);
        }
    }
}
