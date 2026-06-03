package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Caption configuration for Insights.Image/Video.
 * Contains Enable and Prompt fields.
 */
public final class InsightsCaptionConfig {

    @JsonProperty("Enable")
    @JacksonXmlProperty(localName = "Enable")
    private String enable;

    @JsonProperty("Prompt")
    @JacksonXmlProperty(localName = "Prompt")
    private String prompt;

    public InsightsCaptionConfig() {}

    private InsightsCaptionConfig(Builder builder) {
        this.enable = builder.enable;
        this.prompt = builder.prompt;
    }

    public String enable() { return this.enable; }
    public String prompt() { return this.prompt; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String enable;
        private String prompt;

        public Builder enable(String value) { this.enable = value; return this; }
        public Builder prompt(String value) { this.prompt = value; return this; }

        private Builder() {}
        private Builder(InsightsCaptionConfig from) {
            this.enable = from.enable;
            this.prompt = from.prompt;
        }

        public InsightsCaptionConfig build() { return new InsightsCaptionConfig(this); }
    }
}
