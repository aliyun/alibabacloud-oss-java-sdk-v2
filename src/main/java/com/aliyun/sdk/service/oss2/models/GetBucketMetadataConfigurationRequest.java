package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the GetBucketMetadataConfiguration operation.
 */
public final class GetBucketMetadataConfigurationRequest extends RequestModel {
    private final String bucket;

    private GetBucketMetadataConfigurationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
    }

    public String bucket() {
        return bucket;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder extends RequestModel.Builder<Builder> {
        private String bucket;

        private Builder() {
        }

        private Builder(GetBucketMetadataConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
        }

        public Builder bucket(String value) {
            this.bucket = requireNonNull(value);
            return this;
        }

        public GetBucketMetadataConfigurationRequest build() {
            return new GetBucketMetadataConfigurationRequest(this);
        }
    }
}
