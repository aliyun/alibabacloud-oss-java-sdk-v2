package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketPublicAccessBlock operation.
 */
public final class GetBucketPublicAccessBlockRequest extends RequestModel { 
    private final String bucket;

    private GetBucketPublicAccessBlockRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * Get the bucket name
     * 
     * @return bucket name
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
         * Set the bucket name
         * 
         * @param value bucket name, cannot be null
         * @return Builder object for chained calls
         */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        
        /**
         * Build GetBucketPublicAccessBlockRequest instance
         * 
         * @return GetBucketPublicAccessBlockRequest instance
         */
        public GetBucketPublicAccessBlockRequest build() {
            return new GetBucketPublicAccessBlockRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketPublicAccessBlockRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
