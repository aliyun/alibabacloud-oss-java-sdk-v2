package com.aliyun.sdk.service.oss2.tables.models;

import static java.util.Objects.requireNonNull;

public final class PutTableBucketMaintenanceConfigurationRequest extends TableRequestModel {
    private final String tableBucketARN;
    private final String type;

    private PutTableBucketMaintenanceConfigurationRequest(Builder builder) {
        super(builder);
        this.tableBucketARN = builder.tableBucketARN;
        this.type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String tableBucketARN() {
        return tableBucketARN;
    }

    public String type() {
        return type;
    }

    public TableBucketMaintenanceConfigurationValue value() {
        return (TableBucketMaintenanceConfigurationValue) this.bodyFields.get("value");
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {
        private String tableBucketARN;
        private String type;

        private Builder() {
            super();
        }

        private Builder(PutTableBucketMaintenanceConfigurationRequest from) {
            super(from);
            this.tableBucketARN = from.tableBucketARN;
            this.type = from.type;
        }

        public Builder tableBucketARN(String value) {
            requireNonNull(value);
            this.tableBucketARN = value;
            return this;
        }

        public Builder type(String value) {
            requireNonNull(value);
            this.type = value;
            return this;
        }

        public Builder value(TableBucketMaintenanceConfigurationValue value) {
            this.bodyFields.put("value", value);
            return this;
        }

        public PutTableBucketMaintenanceConfigurationRequest build() {
            return new PutTableBucketMaintenanceConfigurationRequest(this);
        }
    }
}
