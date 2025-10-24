package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketLifecycle operation.
 */
public final class GetBucketLifecycleRequest extends RequestModel { 
    private final String bucket;

    private GetBucketLifecycleRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
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
    
        
        public GetBucketLifecycleRequest build() {
            return new GetBucketLifecycleRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketLifecycleRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
