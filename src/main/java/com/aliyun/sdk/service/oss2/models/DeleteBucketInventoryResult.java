package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketInventory operation.
 */
public final class DeleteBucketInventoryResult extends ResultModel { 

    DeleteBucketInventoryResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public DeleteBucketInventoryResult build() {
            return new DeleteBucketInventoryResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(DeleteBucketInventoryResult result) {
            super(result);
        }
    }
}
