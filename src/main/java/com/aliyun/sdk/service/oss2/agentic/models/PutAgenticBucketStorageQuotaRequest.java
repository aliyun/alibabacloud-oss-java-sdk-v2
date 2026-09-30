package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.QuotaConfiguration;
import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketStorageQuota operation.
 */
public final class PutAgenticBucketStorageQuotaRequest extends RequestModel {
    private final String bucket;
    private final QuotaConfiguration quotaConfiguration;

    private PutAgenticBucketStorageQuotaRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.quotaConfiguration = builder.quotaConfiguration;
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
    public QuotaConfiguration quotaConfiguration() {
        return quotaConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private QuotaConfiguration quotaConfiguration;

        private Builder() {
            super();
        }

        private Builder(PutAgenticBucketStorageQuotaRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.quotaConfiguration = request.quotaConfiguration;
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
        public Builder quotaConfiguration(QuotaConfiguration value) {
            requireNonNull(value);
            this.quotaConfiguration = value;
            return this;
        }

        public PutAgenticBucketStorageQuotaRequest build() {
            return new PutAgenticBucketStorageQuotaRequest(this);
        }
    }
}
