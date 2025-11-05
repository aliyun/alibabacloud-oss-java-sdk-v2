package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketReferer operation.
 */
public final class PutBucketRefererRequest extends RequestModel { 
    private final String bucket;
    private final RefererConfiguration refererConfiguration;

    private PutBucketRefererRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.refererConfiguration = builder.refererConfiguration;
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
    public RefererConfiguration refererConfiguration() {
        return refererConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private RefererConfiguration refererConfiguration;
    
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
        public Builder refererConfiguration(RefererConfiguration value) {
            requireNonNull(value);
            this.refererConfiguration = value;
            return this;        
        }
        
        public PutBucketRefererRequest build() {
            return new PutBucketRefererRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketRefererRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.refererConfiguration = request.refererConfiguration;
        }        
    }

}
