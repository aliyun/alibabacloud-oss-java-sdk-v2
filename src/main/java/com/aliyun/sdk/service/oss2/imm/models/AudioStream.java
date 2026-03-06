package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "AudioStream")
public final class AudioStream {

    @JacksonXmlProperty(localName = "Index")
    private Long index;

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "CodecName")
    private String codecName;

    @JacksonXmlProperty(localName = "CodecLongName")
    private String codecLongName;

    @JacksonXmlProperty(localName = "CodecTimeBase")
    private String codecTimeBase;

    @JacksonXmlProperty(localName = "CodecTagString")
    private String codecTagString;

    @JacksonXmlProperty(localName = "CodecTag")
    private String codecTag;

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

    @JacksonXmlProperty(localName = "Lyric")
    private String lyric;

    @JacksonXmlProperty(localName = "SampleFormat")
    private String sampleFormat;

    @JacksonXmlProperty(localName = "SampleRate")
    private Long sampleRate;

    @JacksonXmlProperty(localName = "Channels")
    private Long channels;

    @JacksonXmlProperty(localName = "ChannelLayout")
    private String channelLayout;

    public AudioStream() {}

    private AudioStream(Builder builder) {
        this.index = builder.index;
        this.language = builder.language;
        this.codecName = builder.codecName;
        this.codecLongName = builder.codecLongName;
        this.codecTimeBase = builder.codecTimeBase;
        this.codecTagString = builder.codecTagString;
        this.codecTag = builder.codecTag;
        this.timeBase = builder.timeBase;
        this.startTime = builder.startTime;
        this.duration = builder.duration;
        this.bitrate = builder.bitrate;
        this.frameCount = builder.frameCount;
        this.lyric = builder.lyric;
        this.sampleFormat = builder.sampleFormat;
        this.sampleRate = builder.sampleRate;
        this.channels = builder.channels;
        this.channelLayout = builder.channelLayout;
    }

    public Long index() { return this.index; }
    public String language() { return this.language; }
    public String codecName() { return this.codecName; }
    public String codecLongName() { return this.codecLongName; }
    public String codecTimeBase() { return this.codecTimeBase; }
    public String codecTagString() { return this.codecTagString; }
    public String codecTag() { return this.codecTag; }
    public String timeBase() { return this.timeBase; }
    public Double startTime() { return this.startTime; }
    public Double duration() { return this.duration; }
    public Long bitrate() { return this.bitrate; }
    public Long frameCount() { return this.frameCount; }
    public String lyric() { return this.lyric; }
    public String sampleFormat() { return this.sampleFormat; }
    public Long sampleRate() { return this.sampleRate; }
    public Long channels() { return this.channels; }
    public String channelLayout() { return this.channelLayout; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private Long index;
        private String language;
        private String codecName;
        private String codecLongName;
        private String codecTimeBase;
        private String codecTagString;
        private String codecTag;
        private String timeBase;
        private Double startTime;
        private Double duration;
        private Long bitrate;
        private Long frameCount;
        private String lyric;
        private String sampleFormat;
        private Long sampleRate;
        private Long channels;
        private String channelLayout;

        public Builder index(Long value) { this.index = value; return this; }
        public Builder language(String value) { this.language = value; return this; }
        public Builder codecName(String value) { this.codecName = value; return this; }
        public Builder codecLongName(String value) { this.codecLongName = value; return this; }
        public Builder codecTimeBase(String value) { this.codecTimeBase = value; return this; }
        public Builder codecTagString(String value) { this.codecTagString = value; return this; }
        public Builder codecTag(String value) { this.codecTag = value; return this; }
        public Builder timeBase(String value) { this.timeBase = value; return this; }
        public Builder startTime(Double value) { this.startTime = value; return this; }
        public Builder duration(Double value) { this.duration = value; return this; }
        public Builder bitrate(Long value) { this.bitrate = value; return this; }
        public Builder frameCount(Long value) { this.frameCount = value; return this; }
        public Builder lyric(String value) { this.lyric = value; return this; }
        public Builder sampleFormat(String value) { this.sampleFormat = value; return this; }
        public Builder sampleRate(Long value) { this.sampleRate = value; return this; }
        public Builder channels(Long value) { this.channels = value; return this; }
        public Builder channelLayout(String value) { this.channelLayout = value; return this; }

        private Builder() { super(); }

        private Builder(AudioStream from) {
            this.index = from.index;
            this.language = from.language;
            this.codecName = from.codecName;
            this.codecLongName = from.codecLongName;
            this.codecTimeBase = from.codecTimeBase;
            this.codecTagString = from.codecTagString;
            this.codecTag = from.codecTag;
            this.timeBase = from.timeBase;
            this.startTime = from.startTime;
            this.duration = from.duration;
            this.bitrate = from.bitrate;
            this.frameCount = from.frameCount;
            this.lyric = from.lyric;
            this.sampleFormat = from.sampleFormat;
            this.sampleRate = from.sampleRate;
            this.channels = from.channels;
            this.channelLayout = from.channelLayout;
        }

        public AudioStream build() { return new AudioStream(this); }
    }
}
