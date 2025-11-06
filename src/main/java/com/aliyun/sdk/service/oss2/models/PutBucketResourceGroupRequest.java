package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketResourceGroup operation.
 */
public final class PutBucketResourceGroupRequest extends RequestModel {
    private final String bucket;
    private final BucketResourceGroupConfiguration bucketResourceGroupConfiguration;

    private PutBucketResourceGroupRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.bucketResourceGroupConfiguration = builder.bucketResourceGroupConfiguration;
    }

    /**
     * The bucket for which you want to modify the ID of the resource group.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The request body schema.
     */
    public BucketResourceGroupConfiguration bucketResourceGroupConfiguration() {
        return bucketResourceGroupConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private BucketResourceGroupConfiguration bucketResourceGroupConfiguration;

        /**
         * The bucket for which you want to modify the ID of the resource group.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The request body schema.
         */
        public Builder bucketResourceGroupConfiguration(BucketResourceGroupConfiguration value) {
            requireNonNull(value);
            this.bucketResourceGroupConfiguration = value;
            return this;
        }

        public PutBucketResourceGroupRequest build() {
            return new PutBucketResourceGroupRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketResourceGroupRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.bucketResourceGroupConfiguration = request.bucketResourceGroupConfiguration;
        }
    }
}
