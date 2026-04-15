package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class TableMaintenanceJobStatusValue {

    @JsonProperty("status")
    private String status;

    @JsonProperty("lastRunTimestamp")
    private Instant lastRunTimestamp;

    @JsonProperty("failureMessage")
    private String failureMessage;

    public TableMaintenanceJobStatusValue() {
    }

    private TableMaintenanceJobStatusValue(Builder builder) {
        this.status = builder.status;
        this.lastRunTimestamp = builder.lastRunTimestamp;
        this.failureMessage = builder.failureMessage;
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

    public Instant lastRunTimestamp() {
        return lastRunTimestamp;
    }

    public String failureMessage() {
        return failureMessage;
    }

    public static class Builder {
        private String status;
        private Instant lastRunTimestamp;
        private String failureMessage;

        private Builder() {
        }

        private Builder(TableMaintenanceJobStatusValue model) {
            this.status = model.status;
            this.lastRunTimestamp = model.lastRunTimestamp;
            this.failureMessage = model.failureMessage;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder lastRunTimestamp(Instant lastRunTimestamp) {
            this.lastRunTimestamp = lastRunTimestamp;
            return this;
        }

        public Builder failureMessage(String failureMessage) {
            this.failureMessage = failureMessage;
            return this;
        }

        public TableMaintenanceJobStatusValue build() {
            return new TableMaintenanceJobStatusValue(this);
        }
    }
}
