package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioStream {

    @JsonProperty("Index")
    private Long index;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("CodecName")
    private String codecName;

    @JsonProperty("CodecLongName")
    private String codecLongName;

    @JsonProperty("CodecTimeBase")
    private String codecTimeBase;

    @JsonProperty("CodecTagString")
    private String codecTagString;

    @JsonProperty("CodecTag")
    private String codecTag;

    @JsonProperty("TimeBase")
    private String timeBase;

    @JsonProperty("StartTime")
    private Double startTime;

    @JsonProperty("Duration")
    private Double duration;

    @JsonProperty("Bitrate")
    private Long bitrate;

    @JsonProperty("FrameCount")
    private Long frameCount;

    @JsonProperty("Lyric")
    private String lyric;

    @JsonProperty("SampleFormat")
    private String sampleFormat;

    @JsonProperty("SampleRate")
    private Long sampleRate;

    @JsonProperty("Channels")
    private Long channels;

    @JsonProperty("ChannelLayout")
    private String channelLayout;

    public AudioStream() {
    }

    public Long getIndex() {
        return index;
    }

    public AudioStream setIndex(Long index) {
        this.index = index;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public AudioStream setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCodecName() {
        return codecName;
    }

    public AudioStream setCodecName(String codecName) {
        this.codecName = codecName;
        return this;
    }

    public String getCodecLongName() {
        return codecLongName;
    }

    public AudioStream setCodecLongName(String codecLongName) {
        this.codecLongName = codecLongName;
        return this;
    }

    public String getCodecTimeBase() {
        return codecTimeBase;
    }

    public AudioStream setCodecTimeBase(String codecTimeBase) {
        this.codecTimeBase = codecTimeBase;
        return this;
    }

    public String getCodecTagString() {
        return codecTagString;
    }

    public AudioStream setCodecTagString(String codecTagString) {
        this.codecTagString = codecTagString;
        return this;
    }

    public String getCodecTag() {
        return codecTag;
    }

    public AudioStream setCodecTag(String codecTag) {
        this.codecTag = codecTag;
        return this;
    }

    public String getTimeBase() {
        return timeBase;
    }

    public AudioStream setTimeBase(String timeBase) {
        this.timeBase = timeBase;
        return this;
    }

    public Double getStartTime() {
        return startTime;
    }

    public AudioStream setStartTime(Double startTime) {
        this.startTime = startTime;
        return this;
    }

    public Double getDuration() {
        return duration;
    }

    public AudioStream setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public AudioStream setBitrate(Long bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public Long getFrameCount() {
        return frameCount;
    }

    public AudioStream setFrameCount(Long frameCount) {
        this.frameCount = frameCount;
        return this;
    }

    public String getLyric() {
        return lyric;
    }

    public AudioStream setLyric(String lyric) {
        this.lyric = lyric;
        return this;
    }

    public String getSampleFormat() {
        return sampleFormat;
    }

    public AudioStream setSampleFormat(String sampleFormat) {
        this.sampleFormat = sampleFormat;
        return this;
    }

    public Long getSampleRate() {
        return sampleRate;
    }

    public AudioStream setSampleRate(Long sampleRate) {
        this.sampleRate = sampleRate;
        return this;
    }

    public Long getChannels() {
        return channels;
    }

    public AudioStream setChannels(Long channels) {
        this.channels = channels;
        return this;
    }

    public String getChannelLayout() {
        return channelLayout;
    }

    public AudioStream setChannelLayout(String channelLayout) {
        this.channelLayout = channelLayout;
        return this;
    }
}
