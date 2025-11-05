package com.aliyun.sdk.service.oss2.vectors.models;

import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectors operation.
 */
public final class GetVectorsRequest extends VectorRequestModel {
    private final String bucket;

    private GetVectorsRequest(Builder builder) {
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
     * The list of vector keys to retrieve.
     */
    public List<String> keys() {
        return (List<String>)this.bodyFields.get("keys");
    }

    /**
     * Whether to return vector data.
     */
    public Boolean returnData() {
        return (Boolean) this.bodyFields.get("returnData");
    }

    /**
     * Whether to return vector metadata.
     */
    public Boolean returnMetadata() {
        return (Boolean) this.bodyFields.get("returnMetadata");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends VectorRequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(GetVectorsRequest request) {
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
         * The list of vector keys to retrieve.
         */
        public Builder keys(List<String> value) {
            requireNonNull(value);
            this.bodyFields.put("keys", value);
            return this;
        }

        /**
         * Whether to return vector data.
         */
        public Builder returnData(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnData", value);
            return this;
        }

        /**
         * Whether to return vector metadata.
         */
        public Builder returnMetadata(Boolean value) {
            requireNonNull(value);
            this.bodyFields.put("returnMetadata", value);
            return this;
        }

        public GetVectorsRequest build() {
            return new GetVectorsRequest(this);
        }
    }
}