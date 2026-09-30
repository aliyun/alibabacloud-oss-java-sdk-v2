package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UpdateBucketMetadataInventoryTableConfiguration operation.
 */
public final class UpdateBucketMetadataInventoryTableConfigurationRequest extends RequestModel {
    private final String bucket;
    private final InventoryTableConfiguration inventoryTableConfiguration;

    private UpdateBucketMetadataInventoryTableConfigurationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.inventoryTableConfiguration = builder.inventoryTableConfiguration;
    }

    public String bucket() {
        return bucket;
    }

    public InventoryTableConfiguration inventoryTableConfiguration() {
        return inventoryTableConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private InventoryTableConfiguration inventoryTableConfiguration;

        private Builder() {
        }

        private Builder(UpdateBucketMetadataInventoryTableConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.inventoryTableConfiguration = request.inventoryTableConfiguration;
        }

        public Builder bucket(String value) {
            this.bucket = requireNonNull(value);
            return this;
        }

        public Builder inventoryTableConfiguration(InventoryTableConfiguration value) {
            this.inventoryTableConfiguration = requireNonNull(value);
            return this;
        }

        public UpdateBucketMetadataInventoryTableConfigurationRequest build() {
            return new UpdateBucketMetadataInventoryTableConfigurationRequest(this);
        }
    }
}
