package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the CreateBucketMetadataConfiguration operation.
 */
public final class CreateBucketMetadataConfigurationResult extends ResultModel {
    private CreateBucketMetadataConfigurationResult(Builder builder) {
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

        private Builder(CreateBucketMetadataConfigurationResult result) {
            super(result);
        }

        public CreateBucketMetadataConfigurationResult build() {
            return new CreateBucketMetadataConfigurationResult(this);
        }
    }
}
