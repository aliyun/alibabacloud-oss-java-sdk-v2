package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "VideoInsight")
public final class VideoInsight {

    @JacksonXmlProperty(localName = "Caption")
    private String caption;

    @JacksonXmlProperty(localName = "Description")
    private String description;

    public VideoInsight() {}

    private VideoInsight(Builder builder) {
        this.caption = builder.caption;
        this.description = builder.description;
    }

    public String caption() { return this.caption; }
    public String description() { return this.description; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String caption;
        private String description;

        public Builder caption(String value) { this.caption = value; return this; }
        public Builder description(String value) { this.description = value; return this; }

        private Builder() { super(); }

        private Builder(VideoInsight from) {
            this.caption = from.caption;
            this.description = from.description;
        }

        public VideoInsight build() { return new VideoInsight(this); }
    }
}
