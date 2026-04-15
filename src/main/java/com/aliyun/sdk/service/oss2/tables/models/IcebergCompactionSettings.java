package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcebergCompactionSettings {

    @JsonProperty("targetFileSizeMB")
    private Integer targetFileSizeMB;

    @JsonProperty("strategy")
    private String strategy;

    public IcebergCompactionSettings() {
    }

    private IcebergCompactionSettings(Builder builder) {
        this.targetFileSizeMB = builder.targetFileSizeMB;
        this.strategy = builder.strategy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer targetFileSizeMB() {
        return targetFileSizeMB;
    }

    public String strategy() {
        return strategy;
    }

    public static class Builder {
        private Integer targetFileSizeMB;
        private String strategy;

        private Builder() {
        }

        private Builder(IcebergCompactionSettings model) {
            this.targetFileSizeMB = model.targetFileSizeMB;
            this.strategy = model.strategy;
        }

        public Builder targetFileSizeMB(Integer targetFileSizeMB) {
            this.targetFileSizeMB = targetFileSizeMB;
            return this;
        }

        public Builder strategy(String strategy) {
            this.strategy = strategy;
            return this;
        }

        public IcebergCompactionSettings build() {
            return new IcebergCompactionSettings(this);
        }
    }
}
