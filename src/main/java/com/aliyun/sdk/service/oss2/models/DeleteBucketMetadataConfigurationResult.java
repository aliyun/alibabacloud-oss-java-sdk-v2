package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the DeleteBucketMetadataConfiguration operation.
 */
public final class DeleteBucketMetadataConfigurationResult extends ResultModel {
    private DeleteBucketMetadataConfigurationResult(Builder builder) {
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

        private Builder(DeleteBucketMetadataConfigurationResult result) {
            super(result);
        }

        public DeleteBucketMetadataConfigurationResult build() {
            return new DeleteBucketMetadataConfigurationResult(this);
        }
    }
}
