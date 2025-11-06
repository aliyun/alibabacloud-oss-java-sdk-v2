package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketAccessMonitor operation.
 */
public final class PutBucketAccessMonitorRequest extends RequestModel { 
    private final String bucket;
    private final AccessMonitorConfiguration accessMonitorConfiguration;

    private PutBucketAccessMonitorRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.accessMonitorConfiguration = builder.accessMonitorConfiguration;
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
    public AccessMonitorConfiguration accessMonitorConfiguration() {
        return accessMonitorConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private AccessMonitorConfiguration accessMonitorConfiguration;
    
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
        public Builder accessMonitorConfiguration(AccessMonitorConfiguration value) {
            requireNonNull(value);
            this.accessMonitorConfiguration = value;
            return this;        
        }
        
        public PutBucketAccessMonitorRequest build() {
            return new PutBucketAccessMonitorRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketAccessMonitorRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.accessMonitorConfiguration = request.accessMonitorConfiguration;
        }        
    }

}
