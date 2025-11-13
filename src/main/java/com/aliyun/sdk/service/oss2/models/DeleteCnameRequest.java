package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteCname operation.
 */
public final class DeleteCnameRequest extends RequestModel { 
    private final String bucket;
    private final BucketCnameConfiguration bucketCnameConfiguration;

    private DeleteCnameRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.bucketCnameConfiguration = builder.bucketCnameConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The request body schema.
     */
    public BucketCnameConfiguration bucketCnameConfiguration() {
        return bucketCnameConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private BucketCnameConfiguration bucketCnameConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The request body schema.
        */
        public Builder bucketCnameConfiguration(BucketCnameConfiguration value) {
            requireNonNull(value);
            this.bucketCnameConfiguration = value;
            return this;        
        }
        
        public DeleteCnameRequest build() {
            return new DeleteCnameRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteCnameRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.bucketCnameConfiguration = request.bucketCnameConfiguration;
        }        
    }

}
