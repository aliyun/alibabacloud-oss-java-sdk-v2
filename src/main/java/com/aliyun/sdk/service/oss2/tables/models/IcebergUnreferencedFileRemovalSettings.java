package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcebergUnreferencedFileRemovalSettings {

    @JsonProperty("unreferencedDays")
    private Integer unreferencedDays;

    @JsonProperty("nonCurrentDays")
    private Integer nonCurrentDays;

    public IcebergUnreferencedFileRemovalSettings() {
    }

    private IcebergUnreferencedFileRemovalSettings(Builder builder) {
        this.unreferencedDays = builder.unreferencedDays;
        this.nonCurrentDays = builder.nonCurrentDays;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer unreferencedDays() {
        return unreferencedDays;
    }

    public Integer nonCurrentDays() {
        return nonCurrentDays;
    }

    public static class Builder {
        private Integer unreferencedDays;
        private Integer nonCurrentDays;

        private Builder() {
        }

        private Builder(IcebergUnreferencedFileRemovalSettings model) {
            this.unreferencedDays = model.unreferencedDays;
            this.nonCurrentDays = model.nonCurrentDays;
        }

        public Builder unreferencedDays(Integer unreferencedDays) {
            this.unreferencedDays = unreferencedDays;
            return this;
        }

        public Builder nonCurrentDays(Integer nonCurrentDays) {
            this.nonCurrentDays = nonCurrentDays;
            return this;
        }

        public IcebergUnreferencedFileRemovalSettings build() {
            return new IcebergUnreferencedFileRemovalSettings(this);
        }
    }
}
