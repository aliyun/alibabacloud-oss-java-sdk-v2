package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Insights.Image configuration within InsightsConfig.
 * Contains Caption and Label sub-configs.
 */
public final class InsightsImageConfig {

    @JsonProperty("Caption")
    @JacksonXmlProperty(localName = "Caption")
    private InsightsCaptionConfig caption;

    @JsonProperty("Label")
    @JacksonXmlProperty(localName = "Label")
    private InsightsImageLabelConfig label;

    public InsightsImageConfig() {}

    private InsightsImageConfig(Builder builder) {
        this.caption = builder.caption;
        this.label = builder.label;
    }

    public InsightsCaptionConfig caption() { return this.caption; }
    public InsightsImageLabelConfig label() { return this.label; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private InsightsCaptionConfig caption;
        private InsightsImageLabelConfig label;

        public Builder caption(InsightsCaptionConfig value) { this.caption = value; return this; }
        public Builder label(InsightsImageLabelConfig value) { this.label = value; return this; }

        private Builder() {}
        private Builder(InsightsImageConfig from) {
            this.caption = from.caption;
            this.label = from.label;
        }

        public InsightsImageConfig build() { return new InsightsImageConfig(this); }
    }
}
