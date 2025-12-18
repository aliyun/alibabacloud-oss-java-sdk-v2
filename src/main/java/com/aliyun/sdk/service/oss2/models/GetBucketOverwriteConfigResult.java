package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetBucketOverwriteConfig operation.
 */
public final class GetBucketOverwriteConfigResult extends ResultModel {

    GetBucketOverwriteConfigResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Container for Saving Bucket Overwrite Rules
     */
    public OverwriteConfiguration overwriteConfiguration() {
        return (OverwriteConfiguration) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(GetBucketOverwriteConfigResult result) {
            super(result);
        }

        public GetBucketOverwriteConfigResult build() {
            return new GetBucketOverwriteConfigResult(this);
        }
    }
}
