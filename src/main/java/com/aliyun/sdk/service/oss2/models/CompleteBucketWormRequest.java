package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CompleteBucketWorm operation.
 */
public final class CompleteBucketWormRequest extends RequestModel { 
    private final String bucket;

    private CompleteBucketWormRequest(Builder builder) {
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
     * The ID of the retention policy.
     */
    public String wormId() {
        String value = parameters.get("wormId");
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
        * The ID of the retention policy.
        */
        public Builder wormId(String value) {
            requireNonNull(value);
            this.parameters.put("wormId", value);
            return this;        
        }
    
        
        public CompleteBucketWormRequest build() {
            return new CompleteBucketWormRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(CompleteBucketWormRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
