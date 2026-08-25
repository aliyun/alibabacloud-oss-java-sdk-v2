package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketPolicy operation.
 */
public final class PutAgenticBucketPolicyRequest extends RequestModel {
    private final String bucket;
    private final String policy;

    private PutAgenticBucketPolicyRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.policy = builder.policy;
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }

    /**
     * The policy of the bucket, as a JSON string.
     */
    public String policy() { return policy; }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private String policy;
        private Builder() { super(); }
        private Builder(PutAgenticBucketPolicyRequest request) {
            super(request); this.bucket = request.bucket; this.policy = request.policy;
        }
        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) { requireNonNull(value); this.bucket = value; return this; }

        /**
         * The policy of the bucket, as a JSON string.
         */
        public Builder policy(String value) { this.policy = value; return this; }
        public PutAgenticBucketPolicyRequest build() { return new PutAgenticBucketPolicyRequest(this); }
    }
}
