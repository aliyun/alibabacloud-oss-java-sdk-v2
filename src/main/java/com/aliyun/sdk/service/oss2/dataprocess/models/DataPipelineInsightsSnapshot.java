package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Snapshot settings for data pipeline insights.
 */
@JacksonXmlRootElement(localName = "Snapshot")
public final class DataPipelineInsightsSnapshot {
    @JacksonXmlProperty(localName = "Mode")
    private String mode;

    @JacksonXmlProperty(localName = "Interval")
    private Double interval;

    @JacksonXmlProperty(localName = "Number")
    private Integer number;

    public DataPipelineInsightsSnapshot() {
    }

    private DataPipelineInsightsSnapshot(Builder builder) {
        this.mode = builder.mode;
        this.interval = builder.interval;
        this.number = builder.number;
    }

    public String mode() {
        return mode;
    }

    public Double interval() {
        return interval;
    }

    public Integer number() {
        return number;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String mode;
        private Double interval;
        private Integer number;

        public Builder mode(String value) {
            this.mode = value;
            return this;
        }

        public Builder interval(Double value) {
            this.interval = value;
            return this;
        }

        public Builder number(Integer value) {
            this.number = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineInsightsSnapshot from) {
            this.mode = from.mode;
            this.interval = from.interval;
            this.number = from.number;
        }

        public DataPipelineInsightsSnapshot build() {
            return new DataPipelineInsightsSnapshot(this);
        }
    }
}
