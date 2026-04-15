package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class GetTableBucketMaintenanceConfigurationRequest extends TableRequestModel {
    private final String tableBucketARN;

    private GetTableBucketMaintenanceConfigurationRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;

        private Builder() {
            super();
        }

        private Builder(GetTableBucketMaintenanceConfigurationRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public GetTableBucketMaintenanceConfigurationRequest build() {
            return new GetTableBucketMaintenanceConfigurationRequest(this);
        }
    }
}
