package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketStorageQuota operation.
 */
public final class PutAgenticBucketStorageQuotaRequest extends RequestModel {
    private final String bucket;
    private final BucketStorageQuotaConfiguration bucketStorageQuotaConfiguration;

    private PutAgenticBucketStorageQuotaRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.bucketStorageQuotaConfiguration = builder.bucketStorageQuotaConfiguration;
    }

    /**
     * The name of the agentic bucket.
     */
    public String bucket() {
        return bucket;
    }

    /**
     * The container of the request body.
     */
    public BucketStorageQuotaConfiguration bucketStorageQuotaConfiguration() {
        return bucketStorageQuotaConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private BucketStorageQuotaConfiguration bucketStorageQuotaConfiguration;

        private Builder() {
            super();
        }

        private Builder(PutAgenticBucketStorageQuotaRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.bucketStorageQuotaConfiguration = request.bucketStorageQuotaConfiguration;
        }

        /**
         * The name of the agentic bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        /**
         * The container of the request body.
         */
        public Builder bucketStorageQuotaConfiguration(BucketStorageQuotaConfiguration value) {
            requireNonNull(value);
            this.bucketStorageQuotaConfiguration = value;
            return this;
        }

        public PutAgenticBucketStorageQuotaRequest build() {
            return new PutAgenticBucketStorageQuotaRequest(this);
        }
    }
}
