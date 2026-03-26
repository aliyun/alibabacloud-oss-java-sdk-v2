package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the PutBucketObjectWormConfiguration operation.
 */
public final class PutBucketObjectWormConfigurationResult extends ResultModel {

    private PutBucketObjectWormConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public PutBucketObjectWormConfigurationResult build() {
            return new PutBucketObjectWormConfigurationResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutBucketObjectWormConfigurationResult result) {
            super(result);
        }
    }
}
