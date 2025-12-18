package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteBucketOverwriteConfig operation.
 */
public final class DeleteBucketOverwriteConfigRequest extends RequestModel { 
    private final String bucket;

    private DeleteBucketOverwriteConfigRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }
    
    /**
     * Bucket name
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
        * Bucket name
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        
        public DeleteBucketOverwriteConfigRequest build() {
            return new DeleteBucketOverwriteConfigRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketOverwriteConfigRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
