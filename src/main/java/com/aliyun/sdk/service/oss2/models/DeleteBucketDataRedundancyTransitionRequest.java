package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteBucketDataRedundancyTransition operation.
 */
public final class DeleteBucketDataRedundancyTransitionRequest extends RequestModel { 
    private final String bucket;

    private DeleteBucketDataRedundancyTransitionRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The ID of the redundancy type change task.
     */
    public String redundancyTransitionTaskid() {
        String value = parameters.get("x-oss-redundancy-transition-taskid");
        return value;
    }
    

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The ID of the redundancy type change task.
        */
        public Builder redundancyTransitionTaskid(String value) {
            requireNonNull(value);
            this.parameters.put("x-oss-redundancy-transition-taskid", value);
            return this;        
        }
    
        
        public DeleteBucketDataRedundancyTransitionRequest build() {
            return new DeleteBucketDataRedundancyTransitionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketDataRedundancyTransitionRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
