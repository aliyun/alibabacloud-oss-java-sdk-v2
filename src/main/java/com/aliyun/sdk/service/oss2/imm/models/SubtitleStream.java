package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubtitleStream {

    @JsonProperty("Index")
    private Long index;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("CodecName")
    private String codecName;

    @JsonProperty("CodecLongName")
    private String codecLongName;

    @JsonProperty("CodecTagString")
    private String codecTagString;

    @JsonProperty("CodecTag")
    private String codecTag;

    @JsonProperty("StartTime")
    private Double startTime;

    @JsonProperty("Duration")
    private Double duration;

    @JsonProperty("Bitrate")
    private Long bitrate;

    @JsonProperty("Content")
    private String content;

    @JsonProperty("Width")
    private Long width;

    @JsonProperty("Height")
    private Long height;

    public SubtitleStream() {
    }

    public Long getIndex() {
        return index;
    }

    public SubtitleStream setIndex(Long index) {
        this.index = index;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public SubtitleStream setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCodecName() {
        return codecName;
    }

    public SubtitleStream setCodecName(String codecName) {
        this.codecName = codecName;
        return this;
    }

    public String getCodecLongName() {
        return codecLongName;
    }

    public SubtitleStream setCodecLongName(String codecLongName) {
        this.codecLongName = codecLongName;
        return this;
    }

    public String getCodecTagString() {
        return codecTagString;
    }

    public SubtitleStream setCodecTagString(String codecTagString) {
        this.codecTagString = codecTagString;
        return this;
    }

    public String getCodecTag() {
        return codecTag;
    }

    public SubtitleStream setCodecTag(String codecTag) {
        this.codecTag = codecTag;
        return this;
    }

    public Double getStartTime() {
        return startTime;
    }

    public SubtitleStream setStartTime(Double startTime) {
        this.startTime = startTime;
        return this;
    }

    public Double getDuration() {
        return duration;
    }

    public SubtitleStream setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public SubtitleStream setBitrate(Long bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SubtitleStream setContent(String content) {
        this.content = content;
        return this;
    }

    public Long getWidth() {
        return width;
    }

    public SubtitleStream setWidth(Long width) {
        this.width = width;
        return this;
    }

    public Long getHeight() {
        return height;
    }

    public SubtitleStream setHeight(Long height) {
        this.height = height;
        return this;
    }
}
