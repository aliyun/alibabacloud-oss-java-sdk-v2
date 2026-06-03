package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * ReverseImage configuration within DatasetConfig.
 * Controls image/video reverse-image-search capabilities.
 */
public final class ReverseImageConfig {

    @JsonProperty("Image")
    @JacksonXmlProperty(localName = "Image")
    private EnableConfig image;

    @JsonProperty("Video")
    @JacksonXmlProperty(localName = "Video")
    private EnableConfig video;

    public ReverseImageConfig() {}

    private ReverseImageConfig(Builder builder) {
        this.image = builder.image;
        this.video = builder.video;
    }

    public EnableConfig image() { return this.image; }
    public EnableConfig video() { return this.video; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private EnableConfig image;
        private EnableConfig video;

        public Builder image(EnableConfig value) { this.image = value; return this; }
        public Builder video(EnableConfig value) { this.video = value; return this; }

        private Builder() {}
        private Builder(ReverseImageConfig from) {
            this.image = from.image;
            this.video = from.video;
        }

        public ReverseImageConfig build() { return new ReverseImageConfig(this); }
    }
}
