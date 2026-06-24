package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "DatasetConfig")
public final class DatasetConfig {

    @JsonProperty("ReverseImage")
    @JacksonXmlProperty(localName = "ReverseImage")
    private ReverseImageConfig reverseImage;

    @JsonProperty("Insights")
    @JacksonXmlProperty(localName = "Insights")
    private InsightsConfig insights;

    @JsonProperty("SmartCluster")
    @JacksonXmlProperty(localName = "SmartCluster")
    private SmartClusterConfig smartCluster;

    public DatasetConfig() {
    }

    private DatasetConfig(Builder builder) {
        this.reverseImage = builder.reverseImage;
        this.insights = builder.insights;
        this.smartCluster = builder.smartCluster;
    }

    public ReverseImageConfig reverseImage() {
        return this.reverseImage;
    }

    public InsightsConfig insights() {
        return this.insights;
    }

    public SmartClusterConfig smartCluster() {
        return this.smartCluster;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private ReverseImageConfig reverseImage;
        private InsightsConfig insights;
        private SmartClusterConfig smartCluster;

        public Builder reverseImage(ReverseImageConfig value) {
            this.reverseImage = value;
            return this;
        }

        public Builder insights(InsightsConfig value) {
            this.insights = value;
            return this;
        }

        public Builder smartCluster(SmartClusterConfig value) {
            this.smartCluster = value;
            return this;
        }

        private Builder() {
            super();
        }

        private Builder(DatasetConfig from) {
            this.reverseImage = from.reverseImage;
            this.insights = from.insights;
            this.smartCluster = from.smartCluster;
        }

        public DatasetConfig build() {
            return new DatasetConfig(this);
        }
    }
}
