package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * A generic configuration model containing a single Enable field.
 * Reused by multiple nested structures in DatasetConfig.
 */
public final class EnableConfig {

    @JsonProperty("Enable")
    @JacksonXmlProperty(localName = "Enable")
    private String enable;

    public EnableConfig() {}

    private EnableConfig(Builder builder) {
        this.enable = builder.enable;
    }

    public String enable() { return this.enable; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String enable;

        public Builder enable(String value) { this.enable = value; return this; }
        public Builder enable(Boolean value) { this.enable = value != null ? value.toString() : null; return this; }

        private Builder() {}
        private Builder(EnableConfig from) {
            this.enable = from.enable;
        }

        public EnableConfig build() { return new EnableConfig(this); }
    }
}
