package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableBucketMaintenanceSettings {

    @JsonProperty("icebergUnreferencedFileRemoval")
    private IcebergUnreferencedFileRemovalSettings icebergUnreferencedFileRemoval;

    public TableBucketMaintenanceSettings() {
    }

    private TableBucketMaintenanceSettings(Builder builder) {
        this.icebergUnreferencedFileRemoval = builder.icebergUnreferencedFileRemoval;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public IcebergUnreferencedFileRemovalSettings icebergUnreferencedFileRemoval() {
        return icebergUnreferencedFileRemoval;
    }

    public static class Builder {
        private IcebergUnreferencedFileRemovalSettings icebergUnreferencedFileRemoval;

        private Builder() {
        }

        private Builder(TableBucketMaintenanceSettings model) {
            this.icebergUnreferencedFileRemoval = model.icebergUnreferencedFileRemoval;
        }

        public Builder icebergUnreferencedFileRemoval(IcebergUnreferencedFileRemovalSettings icebergUnreferencedFileRemoval) {
            this.icebergUnreferencedFileRemoval = icebergUnreferencedFileRemoval;
            return this;
        }

        public TableBucketMaintenanceSettings build() {
            return new TableBucketMaintenanceSettings(this);
        }
    }
}
