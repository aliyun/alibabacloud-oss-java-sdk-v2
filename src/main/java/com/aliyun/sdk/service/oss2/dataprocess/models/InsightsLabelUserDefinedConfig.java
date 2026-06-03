package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * UserDefined label configuration within Insights.Video.Label.
 */
public final class InsightsLabelUserDefinedConfig {

    @JsonProperty("Enable")
    @JacksonXmlProperty(localName = "Enable")
    private String enable;

    @JsonProperty("Mode")
    @JacksonXmlProperty(localName = "Mode")
    private String mode;

    @JsonProperty("Labels")
    @JacksonXmlElementWrapper(localName = "Labels")
    @JacksonXmlProperty(localName = "Label")
    private List<InsightsLabelItem> labels;

    public InsightsLabelUserDefinedConfig() {}

    private InsightsLabelUserDefinedConfig(Builder builder) {
        this.enable = builder.enable;
        this.mode = builder.mode;
        this.labels = builder.labels;
    }

    public String enable() { return this.enable; }
    public String mode() { return this.mode; }
    public List<InsightsLabelItem> labels() { return this.labels; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String enable;
        private String mode;
        private List<InsightsLabelItem> labels;

        public Builder enable(String value) { this.enable = value; return this; }
        public Builder mode(String value) { this.mode = value; return this; }
        public Builder labels(List<InsightsLabelItem> value) { this.labels = value; return this; }

        private Builder() {}
        private Builder(InsightsLabelUserDefinedConfig from) {
            this.enable = from.enable;
            this.mode = from.mode;
            this.labels = from.labels;
        }

        public InsightsLabelUserDefinedConfig build() { return new InsightsLabelUserDefinedConfig(this); }
    }
}
