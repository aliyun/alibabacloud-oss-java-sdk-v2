package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketStatus operation.
 */
public final class PutAgenticBucketStatusRequest extends RequestModel {
    private final String bucket;
    private final AgenticBucketStatus agenticBucketStatus;

    private PutAgenticBucketStatusRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.agenticBucketStatus = builder.agenticBucketStatus;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }

    /**
     * The status of the agentic bucket.
     */
    public AgenticBucketStatus agenticBucketStatus() { return agenticBucketStatus; }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private AgenticBucketStatus agenticBucketStatus;

        private Builder() { super(); }

        private Builder(PutAgenticBucketStatusRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.agenticBucketStatus = request.agenticBucketStatus;
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
         * The status of the agentic bucket.
         */
        public Builder agenticBucketStatus(AgenticBucketStatus value) {
            this.agenticBucketStatus = value;
            return this;
        }

        public PutAgenticBucketStatusRequest build() {
            return new PutAgenticBucketStatusRequest(this);
        }
    }
}
