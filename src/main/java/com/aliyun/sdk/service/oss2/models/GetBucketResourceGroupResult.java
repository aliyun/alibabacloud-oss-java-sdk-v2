package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketResourceGroup operation.
 */
public final class GetBucketResourceGroupResult extends ResultModel { 

    /**
     * The container that stores the ID of the resource group.
     */
    public BucketResourceGroupConfiguration bucketResourceGroupConfiguration() {
        return (BucketResourceGroupConfiguration)innerBody;
    } 
     

    GetBucketResourceGroupResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketResourceGroupResult build() {
            return new GetBucketResourceGroupResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketResourceGroupResult result) {
            super(result);
        }
    }
}
