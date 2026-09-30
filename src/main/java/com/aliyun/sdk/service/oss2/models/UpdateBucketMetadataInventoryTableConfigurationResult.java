package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the UpdateBucketMetadataInventoryTableConfiguration operation.
 */
public final class UpdateBucketMetadataInventoryTableConfigurationResult extends ResultModel {
    private UpdateBucketMetadataInventoryTableConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
        }

        private Builder(UpdateBucketMetadataInventoryTableConfigurationResult result) {
            super(result);
        }

        public UpdateBucketMetadataInventoryTableConfigurationResult build() {
            return new UpdateBucketMetadataInventoryTableConfigurationResult(this);
        }
    }
}
