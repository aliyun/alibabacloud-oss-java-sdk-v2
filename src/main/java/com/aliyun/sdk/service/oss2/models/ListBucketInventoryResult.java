package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the ListBucketInventory operation.
 */
public final class ListBucketInventoryResult extends ResultModel { 

    /**
     * The container that stores inventory configuration list.
     */
    public ListInventoryConfigurationsResult listInventoryConfigurationsResult() {
        return (ListInventoryConfigurationsResult)innerBody;
    } 
     

    ListBucketInventoryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public ListBucketInventoryResult build() {
            return new ListBucketInventoryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(ListBucketInventoryResult result) {
            super(result);
        }
    }
}
