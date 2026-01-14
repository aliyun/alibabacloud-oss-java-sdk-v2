package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the ListBucketInventory operation.
 */
public final class ListBucketInventoryRequest extends RequestModel { 
    private final String bucket;

    private ListBucketInventoryRequest(Builder builder) {
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
     * Specify the start position of the list operation. You can obtain this token from the NextContinuationToken field of last ListBucketInventory's result.
     */
    public String continuationToken() {
        String value = parameters.get("continuation-token");
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
        * Specify the start position of the list operation. You can obtain this token from the NextContinuationToken field of last ListBucketInventory's result.
        */
        public Builder continuationToken(String value) {
            requireNonNull(value);
            this.parameters.put("continuation-token", value);
            return this;        
        }
    
        
        public ListBucketInventoryRequest build() {
            return new ListBucketInventoryRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListBucketInventoryRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
