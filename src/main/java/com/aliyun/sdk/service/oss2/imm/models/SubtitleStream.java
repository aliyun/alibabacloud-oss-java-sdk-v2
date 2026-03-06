package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "SubtitleStream")
public final class SubtitleStream {

    @JacksonXmlProperty(localName = "Index")
    private Long index;

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "CodecName")
    private String codecName;

    @JacksonXmlProperty(localName = "CodecLongName")
    private String codecLongName;

    @JacksonXmlProperty(localName = "CodecTagString")
    private String codecTagString;

    @JacksonXmlProperty(localName = "CodecTag")
    private String codecTag;

    @JacksonXmlProperty(localName = "StartTime")
    private Double startTime;

    @JacksonXmlProperty(localName = "Duration")
    private Double duration;

    @JacksonXmlProperty(localName = "Bitrate")
    private Long bitrate;

    @JacksonXmlProperty(localName = "Content")
    private String content;

    @JacksonXmlProperty(localName = "Width")
    private Long width;

    @JacksonXmlProperty(localName = "Height")
    private Long height;

    public SubtitleStream() {}

    private SubtitleStream(Builder builder) {
        this.index = builder.index;
        this.language = builder.language;
        this.codecName = builder.codecName;
        this.codecLongName = builder.codecLongName;
        this.codecTagString = builder.codecTagString;
        this.codecTag = builder.codecTag;
        this.startTime = builder.startTime;
        this.duration = builder.duration;
        this.bitrate = builder.bitrate;
        this.content = builder.content;
        this.width = builder.width;
        this.height = builder.height;
    }

    public Long index() { return this.index; }
    public String language() { return this.language; }
    public String codecName() { return this.codecName; }
    public String codecLongName() { return this.codecLongName; }
    public String codecTagString() { return this.codecTagString; }
    public String codecTag() { return this.codecTag; }
    public Double startTime() { return this.startTime; }
    public Double duration() { return this.duration; }
    public Long bitrate() { return this.bitrate; }
    public String content() { return this.content; }
    public Long width() { return this.width; }
    public Long height() { return this.height; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private Long index;
        private String language;
        private String codecName;
        private String codecLongName;
        private String codecTagString;
        private String codecTag;
        private Double startTime;
        private Double duration;
        private Long bitrate;
        private String content;
        private Long width;
        private Long height;

        public Builder index(Long value) { this.index = value; return this; }
        public Builder language(String value) { this.language = value; return this; }
        public Builder codecName(String value) { this.codecName = value; return this; }
        public Builder codecLongName(String value) { this.codecLongName = value; return this; }
        public Builder codecTagString(String value) { this.codecTagString = value; return this; }
        public Builder codecTag(String value) { this.codecTag = value; return this; }
        public Builder startTime(Double value) { this.startTime = value; return this; }
        public Builder duration(Double value) { this.duration = value; return this; }
        public Builder bitrate(Long value) { this.bitrate = value; return this; }
        public Builder content(String value) { this.content = value; return this; }
        public Builder width(Long value) { this.width = value; return this; }
        public Builder height(Long value) { this.height = value; return this; }

        private Builder() { super(); }

        private Builder(SubtitleStream from) {
            this.index = from.index;
            this.language = from.language;
            this.codecName = from.codecName;
            this.codecLongName = from.codecLongName;
            this.codecTagString = from.codecTagString;
            this.codecTag = from.codecTag;
            this.startTime = from.startTime;
            this.duration = from.duration;
            this.bitrate = from.bitrate;
            this.content = from.content;
            this.width = from.width;
            this.height = from.height;
        }

        public SubtitleStream build() { return new SubtitleStream(this); }
    }
}
