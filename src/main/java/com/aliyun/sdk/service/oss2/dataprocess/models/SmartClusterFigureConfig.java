package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * SmartCluster.Figure configuration within DatasetConfig.
 */
public final class SmartClusterFigureConfig {

    @JsonProperty("AutoGenerate")
    @JacksonXmlProperty(localName = "AutoGenerate")
    private String autoGenerate;

    @JsonProperty("AutoClustering")
    @JacksonXmlProperty(localName = "AutoClustering")
    private String autoClustering;

    @JsonProperty("MinEntityCount")
    @JacksonXmlProperty(localName = "MinEntityCount")
    private Long minEntityCount;

    @JsonProperty("EnabledFeatures")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "EnabledFeatures")
    private List<String> enabledFeatures;

    public SmartClusterFigureConfig() {}

    private SmartClusterFigureConfig(Builder builder) {
        this.autoGenerate = builder.autoGenerate;
        this.autoClustering = builder.autoClustering;
        this.minEntityCount = builder.minEntityCount;
        this.enabledFeatures = builder.enabledFeatures;
    }

    public String autoGenerate() { return this.autoGenerate; }
    public String autoClustering() { return this.autoClustering; }
    public Long minEntityCount() { return this.minEntityCount; }
    public List<String> enabledFeatures() { return this.enabledFeatures; }

    public static Builder newBuilder() { return new Builder(); }
    public Builder toBuilder() { return new Builder(this); }

    public static class Builder {
        private String autoGenerate;
        private String autoClustering;
        private Long minEntityCount;
        private List<String> enabledFeatures;

        public Builder autoGenerate(String value) { this.autoGenerate = value; return this; }
        public Builder autoGenerate(Boolean value) { this.autoGenerate = value != null ? value.toString() : null; return this; }
        public Builder autoClustering(String value) { this.autoClustering = value; return this; }
        public Builder autoClustering(Boolean value) { this.autoClustering = value != null ? value.toString() : null; return this; }
        public Builder minEntityCount(Long value) { this.minEntityCount = value; return this; }
        public Builder enabledFeatures(List<String> value) { this.enabledFeatures = value; return this; }

        private Builder() {}
        private Builder(SmartClusterFigureConfig from) {
            this.autoGenerate = from.autoGenerate;
            this.autoClustering = from.autoClustering;
            this.minEntityCount = from.minEntityCount;
            this.enabledFeatures = from.enabledFeatures;
        }

        public SmartClusterFigureConfig build() { return new SmartClusterFigureConfig(this); }
    }
}
