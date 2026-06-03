package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * SmartCluster configuration within DatasetConfig.
 */
public final class SmartClusterConfig {

    @JsonProperty("Figure")
    @JacksonXmlProperty(localName = "Figure")
    private SmartClusterFigureConfig figure;

    public SmartClusterConfig() {}

    private SmartClusterConfig(Builder builder) {
        this.figure = builder.figure;
    }

    public SmartClusterFigureConfig figure() { return this.figure; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private SmartClusterFigureConfig figure;

        public Builder figure(SmartClusterFigureConfig value) { this.figure = value; return this; }

        private Builder() {}
        private Builder(SmartClusterConfig from) {
            this.figure = from.figure;
        }

        public SmartClusterConfig build() { return new SmartClusterConfig(this); }
    }
}
