package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketAcl operation.
 */
public final class PutAgenticBucketAclRequest extends RequestModel {
    private final String bucket;

    private PutAgenticBucketAclRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }

    /**
     * The access control list (ACL) of the bucket.
     */
    public String acl() { return headers.get("x-oss-acl"); }

    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() { super(); }
        private Builder(PutAgenticBucketAclRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) { requireNonNull(value); this.bucket = value; return this; }

        /**
         * The access control list (ACL) of the bucket.
         */
        public Builder acl(String value) { requireNonNull(value); this.headers.put("x-oss-acl", value); return this; }

        public PutAgenticBucketAclRequest build() { return new PutAgenticBucketAclRequest(this); }
    }
}
