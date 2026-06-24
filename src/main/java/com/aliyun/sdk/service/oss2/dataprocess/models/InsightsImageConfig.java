package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Insights.Image configuration within InsightsConfig.
 */
public final class InsightsImageConfig {

    @JsonProperty("Caption")
    @JacksonXmlProperty(localName = "Caption")
    private InsightsCaptionConfig caption;

    public InsightsImageConfig() {}

    private InsightsImageConfig(Builder builder) {
        this.caption = builder.caption;
    }

    public InsightsCaptionConfig caption() { return this.caption; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private InsightsCaptionConfig caption;

        public Builder caption(InsightsCaptionConfig value) { this.caption = value; return this; }

        private Builder() {}
        private Builder(InsightsImageConfig from) {
            this.caption = from.caption;
        }

        public InsightsImageConfig build() { return new InsightsImageConfig(this); }
    }
}
