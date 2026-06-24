package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "InsightsConfig")
public final class InsightsConfig {

    @JsonProperty("Language")
    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JsonProperty("Image")
    @JacksonXmlProperty(localName = "Image")
    private InsightsImageConfig image;

    @JsonProperty("Video")
    @JacksonXmlProperty(localName = "Video")
    private InsightsVideoConfig video;

    public InsightsConfig() {
    }

    private InsightsConfig(Builder builder) {
        this.language = builder.language;
        this.image = builder.image;
        this.video = builder.video;
    }

    public String language() {
        return this.language;
    }

    public InsightsImageConfig image() {
        return this.image;
    }

    public InsightsVideoConfig video() {
        return this.video;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String language;
        private InsightsImageConfig image;
        private InsightsVideoConfig video;

        public Builder language(String value) {
            this.language = value;
            return this;
        }

        public Builder image(InsightsImageConfig value) {
            this.image = value;
            return this;
        }

        public Builder video(InsightsVideoConfig value) {
            this.video = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(InsightsConfig from) {
            this.language = from.language;
            this.image = from.image;
            this.video = from.video;
        }

        public InsightsConfig build() {
            return new InsightsConfig(this);
        }
    }
}
