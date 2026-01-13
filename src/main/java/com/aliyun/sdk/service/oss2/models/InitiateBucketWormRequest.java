package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the InitiateBucketWorm operation.
 */
public final class InitiateBucketWormRequest extends RequestModel { 
    private final String bucket;
    private final InitiateWormConfiguration initiateWormConfiguration;

    private InitiateBucketWormRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.initiateWormConfiguration = builder.initiateWormConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The container of the request body.
     */
    public InitiateWormConfiguration initiateWormConfiguration() {
        return initiateWormConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private InitiateWormConfiguration initiateWormConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The container of the request body.
        */
        public Builder initiateWormConfiguration(InitiateWormConfiguration value) {
            requireNonNull(value);
            this.initiateWormConfiguration = value;
            return this;        
        }
        
        public InitiateBucketWormRequest build() {
            return new InitiateBucketWormRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(InitiateBucketWormRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.initiateWormConfiguration = request.initiateWormConfiguration;
        }        
    }

}
