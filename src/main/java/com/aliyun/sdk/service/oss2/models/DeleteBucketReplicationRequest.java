package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteBucketReplication operation.
 */
public final class DeleteBucketReplicationRequest extends RequestModel { 
    private final String bucket;
    private final ReplicationRules replicationRules;

    private DeleteBucketReplicationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.replicationRules = builder.replicationRules;
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
    public ReplicationRules replicationRules() {
        return replicationRules;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private ReplicationRules replicationRules;
    
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
        public Builder replicationRules(ReplicationRules value) {
            requireNonNull(value);
            this.replicationRules = value;
            return this;        
        }
        
        public DeleteBucketReplicationRequest build() {
            return new DeleteBucketReplicationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketReplicationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.replicationRules = request.replicationRules;
        }        
    }

}
