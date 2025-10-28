package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketResourceGroup operation.
 */
public final class GetBucketResourceGroupRequest extends RequestModel { 
    private final String bucket;

    private GetBucketResourceGroupRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * The name of the bucket that you want to query.
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
        * The name of the bucket that you want to query.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        
        public GetBucketResourceGroupRequest build() {
            return new GetBucketResourceGroupRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketResourceGroupRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
