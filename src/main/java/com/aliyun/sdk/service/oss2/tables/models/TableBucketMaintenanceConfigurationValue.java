package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableBucketMaintenanceConfigurationValue {

    @JsonProperty("status")
    private String status;

    @JsonProperty("settings")
    private TableBucketMaintenanceSettings settings;

    public TableBucketMaintenanceConfigurationValue() {
    }

    private TableBucketMaintenanceConfigurationValue(Builder builder) {
        this.status = builder.status;
        this.settings = builder.settings;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String status() {
        return status;
    }

    public TableBucketMaintenanceSettings settings() {
        return settings;
    }

    public static class Builder {
        private String status;
        private TableBucketMaintenanceSettings settings;

        private Builder() {
        }

        private Builder(TableBucketMaintenanceConfigurationValue model) {
            this.status = model.status;
            this.settings = model.settings;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder settings(TableBucketMaintenanceSettings settings) {
            this.settings = settings;
            return this;
        }

        public TableBucketMaintenanceConfigurationValue build() {
            return new TableBucketMaintenanceConfigurationValue(this);
        }
    }
}
