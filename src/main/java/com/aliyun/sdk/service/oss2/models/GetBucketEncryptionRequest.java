package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketEncryption operation.
 */
public final class GetBucketEncryptionRequest extends RequestModel { 
    private final String bucket;

    private GetBucketEncryptionRequest(Builder builder) {
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
    
        
        public GetBucketEncryptionRequest build() {
            return new GetBucketEncryptionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketEncryptionRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
