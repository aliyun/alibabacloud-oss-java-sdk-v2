package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketTags operation.
 */
public final class PutBucketTagsRequest extends RequestModel { 
    private final String bucket;
    private final Tagging tagging;

    private PutBucketTagsRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.tagging = builder.tagging;
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
    public Tagging tagging() {
        return tagging;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private Tagging tagging;
    
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
        public Builder tagging(Tagging value) {
            requireNonNull(value);
            this.tagging = value;
            return this;        
        }
        
        public PutBucketTagsRequest build() {
            return new PutBucketTagsRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketTagsRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.tagging = request.tagging;
        }        
    }

}
