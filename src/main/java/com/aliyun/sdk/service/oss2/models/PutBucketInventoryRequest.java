package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutBucketInventory operation.
 */
public final class PutBucketInventoryRequest extends RequestModel { 
    private final String bucket;
    private final InventoryConfiguration inventoryConfiguration;

    private PutBucketInventoryRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.inventoryConfiguration = builder.inventoryConfiguration;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The name of the inventory.
     */
    public String inventoryId() {
        String value = parameters.get("inventoryId");
        return value;
    }
    
    /**
     * Request body schema.
     */
    public InventoryConfiguration inventoryConfiguration() {
        return inventoryConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private InventoryConfiguration inventoryConfiguration;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The name of the inventory.
        */
        public Builder inventoryId(String value) {
            requireNonNull(value);
            this.parameters.put("inventoryId", value);
            return this;        
        }
    
        /**
        * Request body schema.
        */
        public Builder inventoryConfiguration(InventoryConfiguration value) {
            requireNonNull(value);
            this.inventoryConfiguration = value;
            return this;        
        }
        
        public PutBucketInventoryRequest build() {
            return new PutBucketInventoryRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketInventoryRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.inventoryConfiguration = request.inventoryConfiguration;
        }        
    }

}
