package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableMaintenanceSettings {

    @JsonProperty("icebergCompaction")
    private IcebergCompactionSettings icebergCompaction;

    @JsonProperty("icebergSnapshotManagement")
    private IcebergSnapshotManagementSettings icebergSnapshotManagement;

    public TableMaintenanceSettings() {
    }

    private TableMaintenanceSettings(Builder builder) {
        this.icebergCompaction = builder.icebergCompaction;
        this.icebergSnapshotManagement = builder.icebergSnapshotManagement;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public IcebergCompactionSettings icebergCompaction() {
        return icebergCompaction;
    }

    public IcebergSnapshotManagementSettings icebergSnapshotManagement() {
        return icebergSnapshotManagement;
    }

    public static class Builder {
        private IcebergCompactionSettings icebergCompaction;
        private IcebergSnapshotManagementSettings icebergSnapshotManagement;

        private Builder() {
        }

        private Builder(TableMaintenanceSettings model) {
            this.icebergCompaction = model.icebergCompaction;
            this.icebergSnapshotManagement = model.icebergSnapshotManagement;
        }

        public Builder icebergCompaction(IcebergCompactionSettings icebergCompaction) {
            this.icebergCompaction = icebergCompaction;
            return this;
        }

        public Builder icebergSnapshotManagement(IcebergSnapshotManagementSettings icebergSnapshotManagement) {
            this.icebergSnapshotManagement = icebergSnapshotManagement;
            return this;
        }

        public TableMaintenanceSettings build() {
            return new TableMaintenanceSettings(this);
        }
    }
}
