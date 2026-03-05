package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoStream {

    @JsonProperty("Index")
    private Long index;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("CodecName")
    private String codecName;

    @JsonProperty("CodecLongName")
    private String codecLongName;

    @JsonProperty("Profile")
    private String profile;

    @JsonProperty("CodecTimeBase")
    private String codecTimeBase;

    @JsonProperty("CodecTagString")
    private String codecTagString;

    @JsonProperty("CodecTag")
    private String codecTag;

    @JsonProperty("Width")
    private Long width;

    @JsonProperty("Height")
    private Long height;

    @JsonProperty("HasBFrames")
    private Long hasBFrames;

    @JsonProperty("SampleAspectRatio")
    private String sampleAspectRatio;

    @JsonProperty("DisplayAspectRatio")
    private String displayAspectRatio;

    @JsonProperty("PixelFormat")
    private String pixelFormat;

    @JsonProperty("Level")
    private Long level;

    @JsonProperty("FrameRate")
    private String frameRate;

    @JsonProperty("AverageFrameRate")
    private String averageFrameRate;

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

    @JsonProperty("Rotate")
    private String rotate;

    @JsonProperty("BitDepth")
    private Long bitDepth;

    @JsonProperty("ColorSpace")
    private String colorSpace;

    @JsonProperty("ColorRange")
    private String colorRange;

    @JsonProperty("ColorTransfer")
    private String colorTransfer;

    @JsonProperty("ColorPrimaries")
    private String colorPrimaries;

    public VideoStream() {
    }

    public Long getIndex() {
        return index;
    }

    public VideoStream setIndex(Long index) {
        this.index = index;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public VideoStream setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCodecName() {
        return codecName;
    }

    public VideoStream setCodecName(String codecName) {
        this.codecName = codecName;
        return this;
    }

    public String getCodecLongName() {
        return codecLongName;
    }

    public VideoStream setCodecLongName(String codecLongName) {
        this.codecLongName = codecLongName;
        return this;
    }

    public String getProfile() {
        return profile;
    }

    public VideoStream setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public String getCodecTimeBase() {
        return codecTimeBase;
    }

    public VideoStream setCodecTimeBase(String codecTimeBase) {
        this.codecTimeBase = codecTimeBase;
        return this;
    }

    public String getCodecTagString() {
        return codecTagString;
    }

    public VideoStream setCodecTagString(String codecTagString) {
        this.codecTagString = codecTagString;
        return this;
    }

    public String getCodecTag() {
        return codecTag;
    }

    public VideoStream setCodecTag(String codecTag) {
        this.codecTag = codecTag;
        return this;
    }

    public Long getWidth() {
        return width;
    }

    public VideoStream setWidth(Long width) {
        this.width = width;
        return this;
    }

    public Long getHeight() {
        return height;
    }

    public VideoStream setHeight(Long height) {
        this.height = height;
        return this;
    }

    public Long getHasBFrames() {
        return hasBFrames;
    }

    public VideoStream setHasBFrames(Long hasBFrames) {
        this.hasBFrames = hasBFrames;
        return this;
    }

    public String getSampleAspectRatio() {
        return sampleAspectRatio;
    }

    public VideoStream setSampleAspectRatio(String sampleAspectRatio) {
        this.sampleAspectRatio = sampleAspectRatio;
        return this;
    }

    public String getDisplayAspectRatio() {
        return displayAspectRatio;
    }

    public VideoStream setDisplayAspectRatio(String displayAspectRatio) {
        this.displayAspectRatio = displayAspectRatio;
        return this;
    }

    public String getPixelFormat() {
        return pixelFormat;
    }

    public VideoStream setPixelFormat(String pixelFormat) {
        this.pixelFormat = pixelFormat;
        return this;
    }

    public Long getLevel() {
        return level;
    }

    public VideoStream setLevel(Long level) {
        this.level = level;
        return this;
    }

    public String getFrameRate() {
        return frameRate;
    }

    public VideoStream setFrameRate(String frameRate) {
        this.frameRate = frameRate;
        return this;
    }

    public String getAverageFrameRate() {
        return averageFrameRate;
    }

    public VideoStream setAverageFrameRate(String averageFrameRate) {
        this.averageFrameRate = averageFrameRate;
        return this;
    }

    public String getTimeBase() {
        return timeBase;
    }

    public VideoStream setTimeBase(String timeBase) {
        this.timeBase = timeBase;
        return this;
    }

    public Double getStartTime() {
        return startTime;
    }

    public VideoStream setStartTime(Double startTime) {
        this.startTime = startTime;
        return this;
    }

    public Double getDuration() {
        return duration;
    }

    public VideoStream setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public VideoStream setBitrate(Long bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public Long getFrameCount() {
        return frameCount;
    }

    public VideoStream setFrameCount(Long frameCount) {
        this.frameCount = frameCount;
        return this;
    }

    public String getRotate() {
        return rotate;
    }

    public VideoStream setRotate(String rotate) {
        this.rotate = rotate;
        return this;
    }

    public Long getBitDepth() {
        return bitDepth;
    }

    public VideoStream setBitDepth(Long bitDepth) {
        this.bitDepth = bitDepth;
        return this;
    }

    public String getColorSpace() {
        return colorSpace;
    }

    public VideoStream setColorSpace(String colorSpace) {
        this.colorSpace = colorSpace;
        return this;
    }

    public String getColorRange() {
        return colorRange;
    }

    public VideoStream setColorRange(String colorRange) {
        this.colorRange = colorRange;
        return this;
    }

    public String getColorTransfer() {
        return colorTransfer;
    }

    public VideoStream setColorTransfer(String colorTransfer) {
        this.colorTransfer = colorTransfer;
        return this;
    }

    public String getColorPrimaries() {
        return colorPrimaries;
    }

    public VideoStream setColorPrimaries(String colorPrimaries) {
        this.colorPrimaries = colorPrimaries;
        return this;
    }
}
