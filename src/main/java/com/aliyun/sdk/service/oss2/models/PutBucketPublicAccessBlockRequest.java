package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketPublicAccessBlock operation.
 */
public final class PutBucketPublicAccessBlockRequest extends RequestModel { 
    private final String bucket;
    private final BucketPublicAccessBlockConfiguration bucketPublicAccessBlockConfiguration;

    private PutBucketPublicAccessBlockRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.bucketPublicAccessBlockConfiguration = builder.bucketPublicAccessBlockConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * Request body.
     */
    public BucketPublicAccessBlockConfiguration publicAccessBlockConfiguration() {
        return bucketPublicAccessBlockConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private BucketPublicAccessBlockConfiguration bucketPublicAccessBlockConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * Request body.
        */
        public Builder bucketPublicAccessBlockConfiguration(BucketPublicAccessBlockConfiguration value) {
            requireNonNull(value);
            this.bucketPublicAccessBlockConfiguration = value;
            return this;        
        }
        
        public PutBucketPublicAccessBlockRequest build() {
            return new PutBucketPublicAccessBlockRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketPublicAccessBlockRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.bucketPublicAccessBlockConfiguration = request.bucketPublicAccessBlockConfiguration;
        }        
    }

}
