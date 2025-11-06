package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketDataRedundancyTransition operation.
 */
public final class GetBucketDataRedundancyTransitionRequest extends RequestModel { 
    private final String bucket;

    private GetBucketDataRedundancyTransitionRequest(Builder builder) {
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
     * The ID of the redundancy change task.
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
        * The ID of the redundancy change task.
        */
        public Builder redundancyTransitionTaskid(String value) {
            requireNonNull(value);
            this.parameters.put("x-oss-redundancy-transition-taskid", value);
            return this;        
        }
    
        
        public GetBucketDataRedundancyTransitionRequest build() {
            return new GetBucketDataRedundancyTransitionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketDataRedundancyTransitionRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
