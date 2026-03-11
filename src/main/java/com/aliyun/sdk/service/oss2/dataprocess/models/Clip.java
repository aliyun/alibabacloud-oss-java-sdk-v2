package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Clip")
public final class Clip {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TimeRange")
    private List<Long> timeRange;

    public Clip() {
    }

    private Clip(Builder builder) {
        this.timeRange = builder.timeRange;
    }

    public List<Long> timeRange() {
        return this.timeRange;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private List<Long> timeRange;

        public Builder timeRange(List<Long> value) {
            this.timeRange = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(Clip from) {
            this.timeRange = from.timeRange;
        }

        public Clip build() {
            return new Clip(this);
        }
    }
}
