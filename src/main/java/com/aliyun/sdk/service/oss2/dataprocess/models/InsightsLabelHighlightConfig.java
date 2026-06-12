package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Highlight label configuration within Insights.Video.Label.
 */
public final class InsightsLabelHighlightConfig {

    @JsonProperty("Enable")
    @JacksonXmlProperty(localName = "Enable")
    private String enable;

    @JsonProperty("Labels")
    @JacksonXmlElementWrapper(localName = "Labels")
    @JacksonXmlProperty(localName = "Label")
    private List<InsightsLabelItem> labels;

    public InsightsLabelHighlightConfig() {}

    private InsightsLabelHighlightConfig(Builder builder) {
        this.enable = builder.enable;
        this.labels = builder.labels;
    }

    public String enable() { return this.enable; }
    public List<InsightsLabelItem> labels() { return this.labels; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String enable;
        private List<InsightsLabelItem> labels;

        public Builder enable(String value) { this.enable = value; return this; }
        public Builder enable(Boolean value) { this.enable = value != null ? value.toString() : null; return this; }
        public Builder labels(List<InsightsLabelItem> value) { this.labels = value; return this; }

        private Builder() {}
        private Builder(InsightsLabelHighlightConfig from) {
            this.enable = from.enable;
            this.labels = from.labels;
        }

        public InsightsLabelHighlightConfig build() { return new InsightsLabelHighlightConfig(this); }
    }
}
