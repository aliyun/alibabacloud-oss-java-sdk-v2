package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketInventory operation.
 */
public final class GetBucketInventoryResult extends ResultModel { 

    /**
     * The inventory task configured for a bucket.
     */
    public InventoryConfiguration inventoryConfiguration() {
        return (InventoryConfiguration)innerBody;
    } 
     

    GetBucketInventoryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketInventoryResult build() {
            return new GetBucketInventoryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketInventoryResult result) {
            super(result);
        }
    }
}
