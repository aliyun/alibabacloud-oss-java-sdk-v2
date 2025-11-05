package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetVectorBucket operation.
 */
public final class GetVectorBucketRequest extends RequestModel {
    private final String bucket;

    private GetVectorBucketRequest(Builder builder) {
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(GetVectorBucketRequest request) {
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

        public GetVectorBucketRequest build() {
            return new GetVectorBucketRequest(this);
        }
    }
}
