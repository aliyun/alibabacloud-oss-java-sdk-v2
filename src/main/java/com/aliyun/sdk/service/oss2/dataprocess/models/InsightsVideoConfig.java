package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Insights.Video configuration within InsightsConfig.
 * Contains Caption, Label, and MultiStream sub-configs.
 */
public final class InsightsVideoConfig {

    @JsonProperty("Caption")
    @JacksonXmlProperty(localName = "Caption")
    private InsightsVideoCaptionConfig caption;

    @JsonProperty("Label")
    @JacksonXmlProperty(localName = "Label")
    private InsightsVideoLabelConfig label;

    @JsonProperty("MultiStream")
    @JacksonXmlProperty(localName = "MultiStream")
    private EnableConfig multiStream;

    public InsightsVideoConfig() {}

    private InsightsVideoConfig(Builder builder) {
        this.caption = builder.caption;
        this.label = builder.label;
        this.multiStream = builder.multiStream;
    }

    public InsightsVideoCaptionConfig caption() { return this.caption; }
    public InsightsVideoLabelConfig label() { return this.label; }
    public EnableConfig multiStream() { return this.multiStream; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private InsightsVideoCaptionConfig caption;
        private InsightsVideoLabelConfig label;
        private EnableConfig multiStream;

        public Builder caption(InsightsVideoCaptionConfig value) { this.caption = value; return this; }
        public Builder label(InsightsVideoLabelConfig value) { this.label = value; return this; }
        public Builder multiStream(EnableConfig value) { this.multiStream = value; return this; }

        private Builder() {}
        private Builder(InsightsVideoConfig from) {
            this.caption = from.caption;
            this.label = from.label;
            this.multiStream = from.multiStream;
        }

        public InsightsVideoConfig build() { return new InsightsVideoConfig(this); }
    }
}
