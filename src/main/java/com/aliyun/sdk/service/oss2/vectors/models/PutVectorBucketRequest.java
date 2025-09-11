package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutVectorBucket operation.
 */
public final class PutVectorBucketRequest extends RequestModel {
    private final String bucket;

    private PutVectorBucketRequest(Builder builder) {
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
     * The ID of the resource group.
     */
    public String resourceGroupId() {
        return headers.get("x-oss-resource-group-id");
    }

    /**
     * The tagging information for the bucket.
     */
    public String bucketTagging() {
        return headers.get("x-oss-bucket-tagging");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(PutVectorBucketRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * The name of the bucket to create.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The ID of the resource group.
         */
        public Builder resourceGroupId(String value) {
            this.headers.put("x-oss-resource-group-id", value);
            return this;
        }

        /**
         * The tagging information for the bucket.
         */
        public Builder bucketTagging(String value) {
            this.headers.put("x-oss-bucket-tagging", value);
            return this;
        }

        public PutVectorBucketRequest build() {
            return new PutVectorBucketRequest(this);
        }
    }
}

