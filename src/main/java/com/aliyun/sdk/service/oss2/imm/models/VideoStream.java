package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "VideoStream")
public final class VideoStream {

    @JacksonXmlProperty(localName = "Index")
    private Long index;

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "CodecName")
    private String codecName;

    @JacksonXmlProperty(localName = "CodecLongName")
    private String codecLongName;

    @JacksonXmlProperty(localName = "Profile")
    private String profile;

    @JacksonXmlProperty(localName = "CodecTimeBase")
    private String codecTimeBase;

    @JacksonXmlProperty(localName = "CodecTagString")
    private String codecTagString;

    @JacksonXmlProperty(localName = "CodecTag")
    private String codecTag;

    @JacksonXmlProperty(localName = "Width")
    private Long width;

    @JacksonXmlProperty(localName = "Height")
    private Long height;

    @JacksonXmlProperty(localName = "HasBFrames")
    private Long hasBFrames;

    @JacksonXmlProperty(localName = "SampleAspectRatio")
    private String sampleAspectRatio;

    @JacksonXmlProperty(localName = "DisplayAspectRatio")
    private String displayAspectRatio;

    @JacksonXmlProperty(localName = "PixelFormat")
    private String pixelFormat;

    @JacksonXmlProperty(localName = "Level")
    private Long level;

    @JacksonXmlProperty(localName = "FrameRate")
    private String frameRate;

    @JacksonXmlProperty(localName = "AverageFrameRate")
    private String averageFrameRate;

    @JacksonXmlProperty(localName = "TimeBase")
    private String timeBase;

    @JacksonXmlProperty(localName = "StartTime")
    private Double startTime;

    @JacksonXmlProperty(localName = "Duration")
    private Double duration;

    @JacksonXmlProperty(localName = "Bitrate")
    private Long bitrate;

    @JacksonXmlProperty(localName = "FrameCount")
    private Long frameCount;

    @JacksonXmlProperty(localName = "Rotate")
    private String rotate;

    @JacksonXmlProperty(localName = "BitDepth")
    private Long bitDepth;

    @JacksonXmlProperty(localName = "ColorSpace")
    private String colorSpace;

    @JacksonXmlProperty(localName = "ColorRange")
    private String colorRange;

    @JacksonXmlProperty(localName = "ColorTransfer")
    private String colorTransfer;

    @JacksonXmlProperty(localName = "ColorPrimaries")
    private String colorPrimaries;

    public VideoStream() {}

    private VideoStream(Builder builder) {
        this.index = builder.index;
        this.language = builder.language;
        this.codecName = builder.codecName;
        this.codecLongName = builder.codecLongName;
        this.profile = builder.profile;
        this.codecTimeBase = builder.codecTimeBase;
        this.codecTagString = builder.codecTagString;
        this.codecTag = builder.codecTag;
        this.width = builder.width;
        this.height = builder.height;
        this.hasBFrames = builder.hasBFrames;
        this.sampleAspectRatio = builder.sampleAspectRatio;
        this.displayAspectRatio = builder.displayAspectRatio;
        this.pixelFormat = builder.pixelFormat;
        this.level = builder.level;
        this.frameRate = builder.frameRate;
        this.averageFrameRate = builder.averageFrameRate;
        this.timeBase = builder.timeBase;
        this.startTime = builder.startTime;
        this.duration = builder.duration;
        this.bitrate = builder.bitrate;
        this.frameCount = builder.frameCount;
        this.rotate = builder.rotate;
        this.bitDepth = builder.bitDepth;
        this.colorSpace = builder.colorSpace;
        this.colorRange = builder.colorRange;
        this.colorTransfer = builder.colorTransfer;
        this.colorPrimaries = builder.colorPrimaries;
    }

    public Long index() { return this.index; }
    public String language() { return this.language; }
    public String codecName() { return this.codecName; }
    public String codecLongName() { return this.codecLongName; }
    public String profile() { return this.profile; }
    public String codecTimeBase() { return this.codecTimeBase; }
    public String codecTagString() { return this.codecTagString; }
    public String codecTag() { return this.codecTag; }
    public Long width() { return this.width; }
    public Long height() { return this.height; }
    public Long hasBFrames() { return this.hasBFrames; }
    public String sampleAspectRatio() { return this.sampleAspectRatio; }
    public String displayAspectRatio() { return this.displayAspectRatio; }
    public String pixelFormat() { return this.pixelFormat; }
    public Long level() { return this.level; }
    public String frameRate() { return this.frameRate; }
    public String averageFrameRate() { return this.averageFrameRate; }
    public String timeBase() { return this.timeBase; }
    public Double startTime() { return this.startTime; }
    public Double duration() { return this.duration; }
    public Long bitrate() { return this.bitrate; }
    public Long frameCount() { return this.frameCount; }
    public String rotate() { return this.rotate; }
    public Long bitDepth() { return this.bitDepth; }
    public String colorSpace() { return this.colorSpace; }
    public String colorRange() { return this.colorRange; }
    public String colorTransfer() { return this.colorTransfer; }
    public String colorPrimaries() { return this.colorPrimaries; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private Long index;
        private String language;
        private String codecName;
        private String codecLongName;
        private String profile;
        private String codecTimeBase;
        private String codecTagString;
        private String codecTag;
        private Long width;
        private Long height;
        private Long hasBFrames;
        private String sampleAspectRatio;
        private String displayAspectRatio;
        private String pixelFormat;
        private Long level;
        private String frameRate;
        private String averageFrameRate;
        private String timeBase;
        private Double startTime;
        private Double duration;
        private Long bitrate;
        private Long frameCount;
        private String rotate;
        private Long bitDepth;
        private String colorSpace;
        private String colorRange;
        private String colorTransfer;
        private String colorPrimaries;

        public Builder index(Long value) { this.index = value; return this; }
        public Builder language(String value) { this.language = value; return this; }
        public Builder codecName(String value) { this.codecName = value; return this; }
        public Builder codecLongName(String value) { this.codecLongName = value; return this; }
        public Builder profile(String value) { this.profile = value; return this; }
        public Builder codecTimeBase(String value) { this.codecTimeBase = value; return this; }
        public Builder codecTagString(String value) { this.codecTagString = value; return this; }
        public Builder codecTag(String value) { this.codecTag = value; return this; }
        public Builder width(Long value) { this.width = value; return this; }
        public Builder height(Long value) { this.height = value; return this; }
        public Builder hasBFrames(Long value) { this.hasBFrames = value; return this; }
        public Builder sampleAspectRatio(String value) { this.sampleAspectRatio = value; return this; }
        public Builder displayAspectRatio(String value) { this.displayAspectRatio = value; return this; }
        public Builder pixelFormat(String value) { this.pixelFormat = value; return this; }
        public Builder level(Long value) { this.level = value; return this; }
        public Builder frameRate(String value) { this.frameRate = value; return this; }
        public Builder averageFrameRate(String value) { this.averageFrameRate = value; return this; }
        public Builder timeBase(String value) { this.timeBase = value; return this; }
        public Builder startTime(Double value) { this.startTime = value; return this; }
        public Builder duration(Double value) { this.duration = value; return this; }
        public Builder bitrate(Long value) { this.bitrate = value; return this; }
        public Builder frameCount(Long value) { this.frameCount = value; return this; }
        public Builder rotate(String value) { this.rotate = value; return this; }
        public Builder bitDepth(Long value) { this.bitDepth = value; return this; }
        public Builder colorSpace(String value) { this.colorSpace = value; return this; }
        public Builder colorRange(String value) { this.colorRange = value; return this; }
        public Builder colorTransfer(String value) { this.colorTransfer = value; return this; }
        public Builder colorPrimaries(String value) { this.colorPrimaries = value; return this; }

        private Builder() { super(); }

        private Builder(VideoStream from) {
            this.index = from.index;
            this.language = from.language;
            this.codecName = from.codecName;
            this.codecLongName = from.codecLongName;
            this.profile = from.profile;
            this.codecTimeBase = from.codecTimeBase;
            this.codecTagString = from.codecTagString;
            this.codecTag = from.codecTag;
            this.width = from.width;
            this.height = from.height;
            this.hasBFrames = from.hasBFrames;
            this.sampleAspectRatio = from.sampleAspectRatio;
            this.displayAspectRatio = from.displayAspectRatio;
            this.pixelFormat = from.pixelFormat;
            this.level = from.level;
            this.frameRate = from.frameRate;
            this.averageFrameRate = from.averageFrameRate;
            this.timeBase = from.timeBase;
            this.startTime = from.startTime;
            this.duration = from.duration;
            this.bitrate = from.bitrate;
            this.frameCount = from.frameCount;
            this.rotate = from.rotate;
            this.bitDepth = from.bitDepth;
            this.colorSpace = from.colorSpace;
            this.colorRange = from.colorRange;
            this.colorTransfer = from.colorTransfer;
            this.colorPrimaries = from.colorPrimaries;
        }

        public VideoStream build() { return new VideoStream(this); }
    }
}
