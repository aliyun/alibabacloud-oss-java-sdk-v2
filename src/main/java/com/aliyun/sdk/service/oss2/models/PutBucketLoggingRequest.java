package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketLogging operation.
 */
public final class PutBucketLoggingRequest extends RequestModel { 
    private final String bucket;
    private final BucketLoggingStatus bucketLoggingStatus;

    private PutBucketLoggingRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.bucketLoggingStatus = builder.bucketLoggingStatus;
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
    public BucketLoggingStatus bucketLoggingStatus() {
        return bucketLoggingStatus;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private BucketLoggingStatus bucketLoggingStatus;
    
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
        public Builder bucketLoggingStatus(BucketLoggingStatus value) {
            requireNonNull(value);
            this.bucketLoggingStatus = value;
            return this;        
        }
        
        public PutBucketLoggingRequest build() {
            return new PutBucketLoggingRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketLoggingRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.bucketLoggingStatus = request.bucketLoggingStatus;
        }        
    }

}
