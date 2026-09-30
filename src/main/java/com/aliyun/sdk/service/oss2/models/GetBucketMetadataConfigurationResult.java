package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.models.internal.GetBucketMetadataConfigurationResultXml;

import java.util.Optional;

/**
 * The result for the GetBucketMetadataConfiguration operation.
 */
public final class GetBucketMetadataConfigurationResult extends ResultModel {
    private final GetBucketMetadataConfigurationResultXml delegate;

    private GetBucketMetadataConfigurationResult(Builder builder) {
        super(builder);
        this.delegate = (GetBucketMetadataConfigurationResultXml) Optional.ofNullable(innerBody)
                .orElse(new GetBucketMetadataConfigurationResultXml());
    }

    public MetadataConfigurationResult metadataConfigurationResult() {
        return delegate.metadataConfigurationResult;
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

        private Builder(GetBucketMetadataConfigurationResult result) {
            super(result);
        }

        public GetBucketMetadataConfigurationResult build() {
            return new GetBucketMetadataConfigurationResult(this);
        }
    }
}
