package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "SceneElement")
public final class SceneElement {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TimeRange")
    private List<Long> timeRange;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "FrameTime")
    private List<Long> frameTimes;

    @JacksonXmlProperty(localName = "VideoStreamIndex")
    private Long videoStreamIndex;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Label")
    private List<Label> labels;

    public SceneElement() {
    }

    private SceneElement(Builder builder) {
        this.timeRange = builder.timeRange;
        this.frameTimes = builder.frameTimes;
        this.videoStreamIndex = builder.videoStreamIndex;
        this.labels = builder.labels;
    }

    public List<Long> timeRange() {
        return this.timeRange;
    }

    public List<Long> frameTimes() {
        return this.frameTimes;
    }

    public Long videoStreamIndex() {
        return this.videoStreamIndex;
    }

    public List<Label> labels() {
        return this.labels;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<Long> timeRange;
        private List<Long> frameTimes;
        private Long videoStreamIndex;
        private List<Label> labels;

        public Builder timeRange(List<Long> value) {
            this.timeRange = value;
            return this;
        }

        public Builder frameTimes(List<Long> value) {
            this.frameTimes = value;
            return this;
        }

        public Builder videoStreamIndex(Long value) {
            this.videoStreamIndex = value;
            return this;
        }

        public Builder labels(List<Label> value) {
            this.labels = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(SceneElement from) {
            this.timeRange = from.timeRange;
            this.frameTimes = from.frameTimes;
            this.videoStreamIndex = from.videoStreamIndex;
            this.labels = from.labels;
        }

        public SceneElement build() {
            return new SceneElement(this);
        }
    }
}
