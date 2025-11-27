package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketReplicationLocation operation.
 */
public final class GetBucketReplicationLocationRequest extends RequestModel { 
    private final String bucket;

    private GetBucketReplicationLocationRequest(Builder builder) {
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
    
        
        public GetBucketReplicationLocationRequest build() {
            return new GetBucketReplicationLocationRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketReplicationLocationRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
