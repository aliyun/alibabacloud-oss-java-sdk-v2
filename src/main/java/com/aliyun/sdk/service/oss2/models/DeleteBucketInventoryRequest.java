package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the DeleteBucketInventory operation.
 */
public final class DeleteBucketInventoryRequest extends RequestModel { 
    private final String bucket;

    private DeleteBucketInventoryRequest(Builder builder) {
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
     * The name of the inventory that you want to delete.
     */
    public String inventoryId() {
        String value = parameters.get("inventoryId");
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
        * The name of the inventory that you want to delete.
        */
        public Builder inventoryId(String value) {
            requireNonNull(value);
            this.parameters.put("inventoryId", value);
            return this;        
        }
    
        
        public DeleteBucketInventoryRequest build() {
            return new DeleteBucketInventoryRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketInventoryRequest request) {
            super(request);
            this.bucket = request.bucket;
        }        
    }

}
