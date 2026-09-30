package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * V2 processing configuration for a data pipeline.
 */
@JacksonXmlRootElement(localName = "DataPipelineDataProcessConfiguration")
public final class DataPipelineDataProcessConfiguration {
    @JacksonXmlProperty(localName = "SearchMode")
    private String searchMode;

    @JacksonXmlProperty(localName = "Insights")
    private DataPipelineInsights insights;

    public DataPipelineDataProcessConfiguration() {
    }

    private DataPipelineDataProcessConfiguration(Builder builder) {
        this.searchMode = builder.searchMode;
        this.insights = builder.insights;
    }

    public String searchMode() {
        return searchMode;
    }

    public DataPipelineInsights insights() {
        return insights;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private String searchMode;
        private DataPipelineInsights insights;

        public Builder searchMode(String value) {
            this.searchMode = value;
            return this;
        }

        public Builder insights(DataPipelineInsights value) {
            this.insights = value;
            return this;
        }

        private Builder() {
        }

        private Builder(DataPipelineDataProcessConfiguration from) {
            this.searchMode = from.searchMode;
            this.insights = from.insights;
        }

        public DataPipelineDataProcessConfiguration build() {
            return new DataPipelineDataProcessConfiguration(this);
        }
    }
}
