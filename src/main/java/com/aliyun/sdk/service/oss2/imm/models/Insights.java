package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Insights")
public final class Insights {

    @JacksonXmlProperty(localName = "Video")
    private VideoInsight video;

    @JacksonXmlProperty(localName = "Image")
    private ImageInsight image;

    public Insights() {}

    private Insights(Builder builder) {
        this.video = builder.video;
        this.image = builder.image;
    }

    public VideoInsight video() { return this.video; }
    public ImageInsight image() { return this.image; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private VideoInsight video;
        private ImageInsight image;

        public Builder video(VideoInsight value) { this.video = value; return this; }
        public Builder image(ImageInsight value) { this.image = value; return this; }

        private Builder() { super(); }

        private Builder(Insights from) {
            this.video = from.video;
            this.image = from.image;
        }

        public Insights build() { return new Insights(this); }
    }
}
