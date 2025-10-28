package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketEncryption operation.
 */
public final class PutBucketEncryptionRequest extends RequestModel { 
    private final String bucket;
    private final ServerSideEncryptionRule serverSideEncryptionRule;

    private PutBucketEncryptionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.serverSideEncryptionRule = builder.serverSideEncryptionRule;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The request body schema.
     */
    public ServerSideEncryptionRule serverSideEncryptionRule() {
        return serverSideEncryptionRule;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private ServerSideEncryptionRule serverSideEncryptionRule;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The request body schema.
        */
        public Builder serverSideEncryptionRule(ServerSideEncryptionRule value) {
            requireNonNull(value);
            this.serverSideEncryptionRule = value;
            return this;        
        }
        
        public PutBucketEncryptionRequest build() {
            return new PutBucketEncryptionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketEncryptionRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.serverSideEncryptionRule = request.serverSideEncryptionRule;
        }        
    }

}
