package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteBucketEncryption operation.
 */
public final class DeleteBucketEncryptionRequest extends RequestModel { 
    private final String bucket;

    private DeleteBucketEncryptionRequest(Builder builder) {
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
    
        
        public DeleteBucketEncryptionRequest build() {
            return new DeleteBucketEncryptionRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketEncryptionRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }
}
