package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the CreateBucketMetadataConfiguration operation.
 */
public final class CreateBucketMetadataConfigurationRequest extends RequestModel {
    private final String bucket;
    private final MetadataConfiguration metadataConfiguration;

    private CreateBucketMetadataConfigurationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.metadataConfiguration = builder.metadataConfiguration;
    }

    public String bucket() {
        return bucket;
    }

    public MetadataConfiguration metadataConfiguration() {
        return metadataConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private MetadataConfiguration metadataConfiguration;

        private Builder() {
        }

        private Builder(CreateBucketMetadataConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.metadataConfiguration = request.metadataConfiguration;
        }

        public Builder bucket(String value) {
            this.bucket = requireNonNull(value);
            return this;
        }

        public Builder metadataConfiguration(MetadataConfiguration value) {
            this.metadataConfiguration = requireNonNull(value);
            return this;
        }

        public CreateBucketMetadataConfigurationRequest build() {
            return new CreateBucketMetadataConfigurationRequest(this);
        }
    }
}
