package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Video Caption configuration within InsightsConfig.
 * Extends basic caption with PersonReference support.
 */
public final class InsightsVideoCaptionConfig {

    @JsonProperty("Enable")
    @JacksonXmlProperty(localName = "Enable")
    private String enable;

    @JsonProperty("Prompt")
    @JacksonXmlProperty(localName = "Prompt")
    private String prompt;

    @JsonProperty("PersonReference")
    @JacksonXmlProperty(localName = "PersonReference")
    private EnableConfig personReference;

    public InsightsVideoCaptionConfig() {}

    private InsightsVideoCaptionConfig(Builder builder) {
        this.enable = builder.enable;
        this.prompt = builder.prompt;
        this.personReference = builder.personReference;
    }

    public String enable() { return this.enable; }
    public String prompt() { return this.prompt; }
    public EnableConfig personReference() { return this.personReference; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String enable;
        private String prompt;
        private EnableConfig personReference;

        public Builder enable(String value) { this.enable = value; return this; }
        public Builder enable(Boolean value) { this.enable = value != null ? value.toString() : null; return this; }
        public Builder prompt(String value) { this.prompt = value; return this; }
        public Builder personReference(EnableConfig value) { this.personReference = value; return this; }

        private Builder() {}
        private Builder(InsightsVideoCaptionConfig from) {
            this.enable = from.enable;
            this.prompt = from.prompt;
            this.personReference = from.personReference;
        }

        public InsightsVideoCaptionConfig build() { return new InsightsVideoCaptionConfig(this); }
    }
}
