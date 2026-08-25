package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.models.RequestModel;
import com.aliyun.sdk.service.oss2.models.ServerSideEncryptionRule;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutAgenticBucketEncryption operation.
 */
public final class PutAgenticBucketEncryptionRequest extends RequestModel {
    private final String bucket;
    private final ServerSideEncryptionRule serverSideEncryptionRule;

    private PutAgenticBucketEncryptionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.serverSideEncryptionRule = builder.serverSideEncryptionRule;
    }

    public static Builder newBuilder() { return new Builder(); }

    /**
     * The name of the bucket.
     */
    public String bucket() { return bucket; }

    /**
     * The server-side encryption rule of the bucket.
     */
    public ServerSideEncryptionRule serverSideEncryptionRule() { return serverSideEncryptionRule; }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private ServerSideEncryptionRule serverSideEncryptionRule;

        private Builder() { super(); }
        private Builder(PutAgenticBucketEncryptionRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.serverSideEncryptionRule = request.serverSideEncryptionRule;
        }

        /**
         * The name of the bucket.
         */
        public Builder bucket(String value) { requireNonNull(value); this.bucket = value; return this; }

        /**
         * The server-side encryption rule of the bucket.
         */
        public Builder serverSideEncryptionRule(ServerSideEncryptionRule value) { this.serverSideEncryptionRule = value; return this; }
        public PutAgenticBucketEncryptionRequest build() { return new PutAgenticBucketEncryptionRequest(this); }
    }
}
