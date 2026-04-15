package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableMaintenanceConfigurationValue {

    @JsonProperty("status")
    private String status;

    @JsonProperty("settings")
    private TableMaintenanceSettings settings;

    public TableMaintenanceConfigurationValue() {
    }

    private TableMaintenanceConfigurationValue(Builder builder) {
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

    public TableMaintenanceSettings settings() {
        return settings;
    }

    public static class Builder {
        private String status;
        private TableMaintenanceSettings settings;

        private Builder() {
        }

        private Builder(TableMaintenanceConfigurationValue model) {
            this.status = model.status;
            this.settings = model.settings;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder settings(TableMaintenanceSettings settings) {
            this.settings = settings;
            return this;
        }

        public TableMaintenanceConfigurationValue build() {
            return new TableMaintenanceConfigurationValue(this);
        }
    }
}
