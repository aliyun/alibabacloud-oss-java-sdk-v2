package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SceneElement {

    @JsonProperty("TimeRange")
    private List<Long> timeRange;

    @JsonProperty("FrameTimes")
    private List<Long> frameTimes;

    @JsonProperty("VideoStreamIndex")
    private Long videoStreamIndex;

    @JsonProperty("Labels")
    private List<Label> labels;

    public SceneElement() {
    }

    public List<Long> getTimeRange() {
        return timeRange;
    }

    public SceneElement setTimeRange(List<Long> timeRange) {
        this.timeRange = timeRange;
        return this;
    }

    public List<Long> getFrameTimes() {
        return frameTimes;
    }

    public SceneElement setFrameTimes(List<Long> frameTimes) {
        this.frameTimes = frameTimes;
        return this;
    }

    public Long getVideoStreamIndex() {
        return videoStreamIndex;
    }

    public SceneElement setVideoStreamIndex(Long videoStreamIndex) {
        this.videoStreamIndex = videoStreamIndex;
        return this;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public SceneElement setLabels(List<Label> labels) {
        this.labels = labels;
        return this;
    }
}
