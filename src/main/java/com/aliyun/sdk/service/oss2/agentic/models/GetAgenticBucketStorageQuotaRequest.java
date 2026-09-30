package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetAgenticBucketStorageQuota operation.
 */
public final class GetAgenticBucketStorageQuotaRequest extends RequestModel {
    private final String bucket;

    private GetAgenticBucketStorageQuotaRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    /**
     * The name of the agentic bucket.
     */
    public String bucket() {
        return bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
            super();
        }

        private Builder(GetAgenticBucketStorageQuotaRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * The name of the agentic bucket.
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;
        }

        public GetAgenticBucketStorageQuotaRequest build() {
            return new GetAgenticBucketStorageQuotaRequest(this);
        }
    }
}
