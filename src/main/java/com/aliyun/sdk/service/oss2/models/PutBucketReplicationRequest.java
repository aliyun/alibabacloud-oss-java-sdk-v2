package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketReplication operation.
 */
public final class PutBucketReplicationRequest extends RequestModel { 
    private final String bucket;
    private final ReplicationConfiguration replicationConfiguration;

    private PutBucketReplicationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.replicationConfiguration = builder.replicationConfiguration;
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
    public ReplicationConfiguration replicationConfiguration() {
        return replicationConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private ReplicationConfiguration replicationConfiguration;
    
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
        public Builder replicationConfiguration(ReplicationConfiguration value) {
            requireNonNull(value);
            this.replicationConfiguration = value;
            return this;        
        }
        
        public PutBucketReplicationRequest build() {
            return new PutBucketReplicationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketReplicationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.replicationConfiguration = request.replicationConfiguration;
        }        
    }

}
